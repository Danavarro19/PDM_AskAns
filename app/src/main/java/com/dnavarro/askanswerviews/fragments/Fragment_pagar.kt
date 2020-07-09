package com.dnavarro.askanswerviews.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dnavarro.askanswerviews.R
import com.dnavarro.askanswerviews.databinding.FragmentPagosBinding
import com.dnavarro.askanswerviews.retrofit.serviceLoginResponse
import com.dnavarro.askanswerviews.viewmodels.Userviewmodel
import com.google.gson.GsonBuilder
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference


class Fragment_pagar : Fragment() {


    private var binding: FragmentPagosBinding? = null
    private lateinit var publishableKey: String
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var acti: Activity
    private val userModel: Userviewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding = DataBindingUtil.inflate<FragmentPagosBinding>(
            inflater,
            R.layout.fragment_pagos, container, false
        )
        userModel.resetLanzamientos()
        userModel.lanzamientosObtenidos.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(R.id.fragment_lanzamientos)
            }
        })
        binding!!.lifecycleOwner = this
        acti = this.activity!!
        startCheckout()


        return binding!!.root
    }

    private fun displayAlert(
        activity: Activity,
        title: String,
        message: String,
        restartDemo: Boolean = false

    ) {
        this.activity!!.runOnUiThread {
            val builder = AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
            if (restartDemo) {
                builder.setPositiveButton("Restart Demo") { _, _ ->
                    val cardInputWidget = binding!!.layoutinclude.cardInputWidget

                    cardInputWidget.clear()
                    startCheckout()

                }
            } else {
                builder.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                    userModel.getLanzamientos()
                })
            }
            val dialog = builder.create()
            dialog.show()
        }


    }

    private fun startCheckout() {
        val weakActivity = WeakReference<Activity>(this.activity)

        val request = Request.Builder()
            .url(serviceLoginResponse.URI + "stripe-key")
            .get()
            .build()
        serviceLoginResponse.client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    weakActivity.get()?.let {
                        displayAlert(it, "Failed to load page", "Error: $e")
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        weakActivity.get()?.let {
                            displayAlert(
                                it,
                                "Failed to load page",
                                "Error: $response"
                            )
                        }
                    } else {
                        val responseData = response.body?.string()
                        val json = responseData?.let { JSONObject(it) } ?: JSONObject()
                        val publishableKey = json.getString("publishableKey")

                        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
                        stripe = Stripe(serviceLoginResponse.context, publishableKey)
                    }
                }
            } as okhttp3.Callback)

        val payButton: Button = binding!!.layoutinclude.payButton
        payButton.setOnClickListener {
            pay()
        }

    }


    private fun pay() {
        val weakActivity = WeakReference<Activity>(this.activity)
        // Collect card details on the client
        val cardInputWidget = binding!!.layoutinclude.cardInputWidget
        cardInputWidget.paymentMethodCreateParams?.let { params ->
            stripe.createPaymentMethod(
                params,
                callback = object : ApiResultCallback<PaymentMethod> {
                    // Create PaymentMethod failed
                    override fun onError(e: Exception) {
                        weakActivity.get()?.let {
                            displayAlert(it, "Payment failed", "Error: $e")
                        }
                    }

                    override fun onSuccess(result: PaymentMethod) {
                        // Create a PaymentIntent on the server with a PaymentMethod
                        print("Created PaymentMethod")
                        pay(result.id, null)
                    }
                })
        }
    }


    private fun pay(paymentMethod: String?, paymentIntent: String?) {
        val weakActivity = WeakReference<Activity>(this.activity)
        var json = ""
        if (!paymentMethod.isNullOrEmpty()) {
            json = """
                {
                    "useStripeSdk":true,
                    "paymentMethodId":"$paymentMethod",
                    "currency":"usd",
                    "items": [
                        {"id":"${userModel.pagarlanzamienot.value}"}
                    ]
                }
                """
        } else if (!paymentIntent.isNullOrEmpty()) {
            json = """
                {
                    "paymentIntentId":"$paymentIntent"
                }
                """
        }
        // Create a PaymentIntent on the server
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(serviceLoginResponse.URI + "pay")
            .post(body as RequestBody)
            .build()
        serviceLoginResponse.client.newCall(request as okhttp3.Request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    weakActivity.get()?.let {
                        displayAlert(it, "Payment failed", "Error: $e")
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    // Request failed
                    if (!response.isSuccessful) {
                        weakActivity.get()?.let {
                            displayAlert(it, "Payment failed", "Error: $response")
                        }
                    } else {
//                        println(response.body?.string())
                        val responseData = response.body!!.string()
                        val responseJson = responseData?.let { JSONObject(it) } ?: JSONObject()
                        val payError: String? = responseJson.optString("error")
                        val clientSecret: String? = responseJson.optString("clientSecret")
                        val requiresAction: Boolean = responseJson.optBoolean("requiresAction")
                        if (payError?.isNotEmpty() == true) {
                            // Payment failed
                            weakActivity.get()?.let {
                                displayAlert(
                                    it,
                                    "Payment failed",
                                    "Error: $payError"
                                )
                            }
                        } else if (clientSecret?.isNotEmpty() == true && !requiresAction) {
                            // Payment succeeded
                            weakActivity.get()?.let {
                                displayAlert(
                                    it,
                                    "Payment succeeded",
                                    "$clientSecret",
                                    restartDemo = false
                                )
                            }
                        }
                        // Payment requires additional actions
                        else if (clientSecret?.isNotEmpty() == true && requiresAction) {
                            acti.runOnUiThread {
                                weakActivity.get()?.let { activity ->
                                    stripe.handleNextActionForPayment(activity, clientSecret)
                                }
                            }


                        }
                    }
                }
            } as okhttp3.Callback)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val weakActivity = WeakReference<Activity>(this.activity)

        // Handle the result of stripe.authenticatePayment
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                when (paymentIntent.status) {
                    StripeIntent.Status.Succeeded -> {
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        weakActivity.get()?.let {
                            displayAlert(
                                it,
                                "Payment succeeded",
                                gson.toJson(paymentIntent),
                                restartDemo = false
                            )
                        }
                    }
                    StripeIntent.Status.RequiresPaymentMethod -> {
                        // Payment failed – allow retrying using a different payment method
                        weakActivity.get()?.let {
                            displayAlert(
                                it,
                                "Payment failed",
                                paymentIntent.lastPaymentError!!.message ?: ""
                            )
                        }
                    }
                    StripeIntent.Status.RequiresConfirmation -> {
                        // After handling a required action on the client, the status of the PaymentIntent is
                        // requires_confirmation. You must send the PaymentIntent ID to your backend
                        // and confirm it to finalize the payment. This step enables your integration to
                        // synchronously fulfill the order on your backend and return the fulfillment result
                        // to your client.
                        print("Re-confirming PaymentIntent after handling a required action")
                        pay(null, paymentIntent.id)
                    }
                    else -> {
                        weakActivity.get()?.let {
                            displayAlert(
                                it,
                                "Payment status unknown",
                                "unhandled status: ${paymentIntent.status}",
                                restartDemo = false
                            )
                        }
                    }
                }
            }

            override fun onError(e: Exception) {
                // Payment request failed – allow retrying using the same payment method
                weakActivity.get()?.let {
                    displayAlert(it, "Payment failed", e.toString())
                }
            }
        })
    }
}