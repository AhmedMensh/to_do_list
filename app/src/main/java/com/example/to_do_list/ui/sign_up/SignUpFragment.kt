package com.example.to_do_list.ui.sign_up

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.to_do_list.databinding.FragmentSignUpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class SignUpFragment : Fragment() {

    private val TAG = "SignUpFragment"
    private lateinit var binding : FragmentSignUpBinding
    private val auth = FirebaseAuth.getInstance()
    private var storedVerificationId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewsClick()
    }
    private fun initViewsClick(){
        binding.btnSignUp.setOnClickListener { verifyPhoneNumber() }
        binding.btnVerify.setOnClickListener {

            val code = java.lang.StringBuilder()
            code.append(binding.etFirstDigit.text.toString())
            code.append(binding.etSecondDigit.text.toString())
            code.append(binding.etThirdDigit.text.toString())
            code.append(binding.etFourthDigit.text.toString())
            code.append(binding.etFifthDigit.text.toString())
            code.append(binding.etSixthDigit.text.toString())
            Log.d(TAG, "initViewsClick: ${code.toString()}")
            verifyPhoneNumberWithCode(storedVerificationId, code.toString())

        }

    }

    private fun verifyPhoneNumber(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.etPhoneNumber.text.toString()) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")

            binding.tiPhoneNumber.visibility = View.GONE
            binding.digitsGroup.visibility = View.VISIBLE
            binding.btnSignUp.visibility = View.GONE
            binding.btnVerify.visibility = View.VISIBLE
            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            val resendToken = token
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    findNavController().navigate(com.example.to_do_list.R.id.tasksFragment)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}