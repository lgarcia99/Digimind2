package com.example.digimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.digimind.R
import com.example.digimind.Task
import com.example.digimind.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var usuario: FirebaseAuth
    private lateinit var storage: FirebaseFirestore

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Instanciar Firebase
        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        val btn_time: Button = root.findViewById(R.id.btn_time)

        btn_time.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                btn_time.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()

        }

        val btn_save = root.findViewById(R.id.btn_save) as Button
        val et_titulo = root.findViewById(R.id.et_task) as EditText
        val checkMonday = root.findViewById(R.id.checkMonday) as CheckBox
        val checkTuesday = root.findViewById(R.id.checkTuesday) as CheckBox
        val checkWednesday = root.findViewById(R.id.checkWednesday) as CheckBox
        val checkThursday = root.findViewById(R.id.checkThursday) as CheckBox
        val checkFriday = root.findViewById(R.id.checkFriday) as CheckBox
        val checkSaturday = root.findViewById(R.id.checkSatuday) as CheckBox
        val checkSunday = root.findViewById(R.id.checkSunday) as CheckBox

        btn_save.setOnClickListener{
            var actividad = hashMapOf(
                "actividad" to et_titulo.text.toString(),
                "email" to usuario.currentUser.email.toString(),
                "lu" to checkMonday.isChecked,
                "ma" to checkTuesday.isChecked,
                "mi" to checkWednesday.isChecked,
                "ju" to checkThursday.isChecked,
                "vi" to checkFriday.isChecked,
                "sa" to checkSaturday.isChecked,
                "do" to checkSunday.isChecked,
                "tiempo" to btn_time.text.toString()
            )

            storage.collection("actividades")
                .add(actividad)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(root.context, "Task added successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(root.context, "Error while adding task.", Toast.LENGTH_SHORT).show()
                }

            /*
            var titulo = et_titulo.text.toString()
            actividad.put("actividad", titulo)

            var time = btn_time.text.toString()
            actividad.put("tiempo", time)

            var days = ArrayList<String>()

            if(checkMonday.isChecked) {
                days.add("Monday")
                actividad.put("lu", true)
            }
            if(checkTuesday.isChecked) {
                days.add("Tuesday")
                actividad.put("ma", true)
            }
            if(checkWednesday.isChecked) {
                days.add("Wednesday")
                actividad.put("mi", true)
            }
            if(checkThursday.isChecked) {
                days.add("Thursday")
                actividad.put("ju", true)
            }
            if(checkFriday.isChecked) {
                days.add("Friday")
                actividad.put("vi", true)
            }
            if(checkSaturday.isChecked) {
                days.add("Saturday")
                actividad.put("sa", true)
            }
            if(checkSunday.isChecked) {
                days.add("Sunday")
                actividad.put("do", true)
            }

            var task = Task(titulo, days, time)

            HomeFragment.tasks.add(task)*/
        }


        return root
    }


}
