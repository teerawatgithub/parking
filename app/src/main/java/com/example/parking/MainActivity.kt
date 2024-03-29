package com.example.parking

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.parking.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var parking: Parking
    private var parkingBlackEnd: ArrayList<Parking> = ArrayList<Parking>(4)
    private var current: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        parkingBlackEnd.add(Parking(" ", " ", " ", " ", false, 1))
        parkingBlackEnd.add(Parking(" ", " ", " ", " ", false, 2))
        parkingBlackEnd.add(Parking(" ", " ", " ", " ", false, 3))
        parkingBlackEnd.add(Parking("Name", "Licen plate", "Car brand", "Tel.", false, 4))
        parking = Parking("Name", "Licen plate", "Car brand", "Tel.", false, 4)
        binding.parking = parking
        changeColorSlotButton()

        binding.apply {
            slot1Button.setOnClickListener{
                current = 1
                showInfo(it, 1)
            }
            slot2Button.setOnClickListener{
                current = 2
                showInfo(it, 2)
            }
            slot3Button.setOnClickListener {
                current = 3
                showInfo(it, 3)
            }

            update_button.setOnClickListener{
                when(current){
                    1 -> updateSlot(it, current)
                    2 -> updateSlot(it, current)
                    3 -> updateSlot(it, current)
                }
            }
            delete_button.setOnClickListener{
                when(current){
                    1 -> clearSlot(it, current)
                    2 -> clearSlot(it, current)
                    3 -> clearSlot(it, current)
                }
            }

        }


    }

    private fun clearSlot(view: View, c: Int) {
        parkingBlackEnd[c-1] = parkingBlackEnd[3]
        parkingBlackEnd[c-1].slotNumber = c
        showInfo(view, c)
        changeColorSlotButton()
    }

    private fun updateSlot(view: View, c: Int) {
        parkingBlackEnd[c-1] = Parking(name_edit_text.text.toString(),
            license_plate_edit_text.text.toString(),
            car_brand_edit_text.text.toString(),
            tel_number_edit_text.text.toString(),
            true, c)

        showInfo(view, c)
        changeColorSlotButton()
    }

    private fun changeColorSlotButton() {
        for (i in 0 until 3){
            if (parkingBlackEnd[i].status){
                when (i){
                    0 -> slot1_button.setBackgroundColor(-0x10000)
                    1 -> slot2_button.setBackgroundColor(-0x10000)
                    2 -> slot3_button.setBackgroundColor(-0x10000)
                }
            }else{
                when (i){
                    0 -> slot1_button.setBackgroundColor(-0xff0100)
                    1 -> slot2_button.setBackgroundColor(-0xff0100)
                    2 -> slot3_button.setBackgroundColor(-0xff0100)

                }
            }
        }
    }

    private fun showInfo(view: View, i: Int) {
        var status = parkingBlackEnd.get(i-1).status
        for (l in 0 until 3){
            if (status && l == i-1){
                binding.parking?.name = parkingBlackEnd.get(l).name
                binding.parking?.licensePlate = parkingBlackEnd[l].licensePlate
                binding.parking?.carBrand = parkingBlackEnd[l].carBrand
                binding.parking?.telNumber = parkingBlackEnd[l].telNumber
                when (l){
                    0 -> slot1_button.text = parkingBlackEnd[l].licensePlate
                    1 -> slot2_button.text = parkingBlackEnd[l].licensePlate
                    2 -> slot3_button.text = parkingBlackEnd[l].licensePlate
                }
                break
            }else if(l == i-1){
                binding.apply {
                    parking?.name = parkingBlackEnd[3].name
                    parking?.licensePlate = parkingBlackEnd[3].licensePlate
                    parking?.carBrand = parkingBlackEnd[3].carBrand
                    parking?.telNumber = parkingBlackEnd[3].telNumber
                }
                when (l){
                    0 -> slot1_button.text = "slot1"
                    1 -> slot2_button.text = "slot2"
                    3 -> slot3_button.text = "slot3"
                }
                break
            }
        }
        tel_number_edit_text.clearFocus()
        car_brand_edit_text.clearFocus()
        name_edit_text.clearFocus()
        license_plate_edit_text.clearFocus()
        binding.invalidateAll()

        val inm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
