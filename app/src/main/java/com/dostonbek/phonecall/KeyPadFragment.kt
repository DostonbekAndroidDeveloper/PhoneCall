package com.dostonbek.phonecall

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.dostonbek.phonecall.databinding.FragmentKeyPadBinding

class KeyPadFragment : Fragment() {
    private lateinit var binding: FragmentKeyPadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentKeyPadBinding.inflate(layoutInflater)
        checkPermission()





        binding.numberZero.setOnClickListener {
            binding.enterNumber.text = addToInputText("0")

        }

        binding.numberOne.setOnClickListener {
            binding.enterNumber.text = addToInputText("1")
        }
        binding.numberTwo.setOnClickListener {
            binding.enterNumber.text = addToInputText("2")
        }
        binding.numberThree.setOnClickListener {
            binding.enterNumber.text = addToInputText("3")
        }
        binding.numberFour.setOnClickListener {
            binding.enterNumber.text = addToInputText("4")
        }
        binding.numberFive.setOnClickListener {
            binding.enterNumber.text = addToInputText("5")
        }
        binding.numberSix.setOnClickListener {
            binding.enterNumber.text = addToInputText("6")
        }
        binding.numberSeven.setOnClickListener {
            binding.enterNumber.text = addToInputText("7")
        }
        binding.numberEight.setOnClickListener {
            binding.enterNumber.text = addToInputText("8")
        }
        binding.numberNine.setOnClickListener {
            binding.enterNumber.text = addToInputText("9")
        }
        binding.star.setOnClickListener {

            binding.enterNumber.text = addToInputText("*")
        }
        binding.hashtag.setOnClickListener {
            binding.enterNumber.text = addToInputText("#")
        }

        binding.numberZero.setOnLongClickListener(
            View.OnLongClickListener {
            binding.enterNumber.text = addToInputText("+")
            true

        })
        binding.deleteBtn.setOnClickListener {
            if (binding.enterNumber.text.isNotEmpty()){
                val removedLast=binding.enterNumber.text.toString().dropLast(1)
                binding.enterNumber.text=removedLast
            }
            else
            {
                Toast.makeText(requireContext(),"Space is empty",Toast.LENGTH_SHORT).show()
            }

        }
        binding.deleteBtn.setOnLongClickListener(View.OnLongClickListener {
            binding.enterNumber.text = ""
            true
        })
        binding.phoneBtn.setOnClickListener {
            var number=binding.enterNumber.text.toString()
            if (number.isNotEmpty()){
                dialPhoneNumber(number)

                //
                 binding.enterNumber.text=""
            }

            else{
                Toast.makeText(requireContext(),"Space is empty", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
    private fun addToInputText(buttonValue:String): String {
        return "${binding.enterNumber.text}$buttonValue"

    }
    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),101)

        }

    }
    private fun dialPhoneNumber(phoneNumber:String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data= Uri.parse("tel:$phoneNumber")


        startActivity(callIntent)
    }


}