package it.sephiroth.android.library.demo

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.sephiroth.android.library.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var checkbox_array: Array<CheckBox>
    private lateinit var viewBinding: ActivityMainBinding
    var listenToUpdates = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.checkBox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (listenToUpdates) {
                listenToUpdates = false
                if (!isChecked) {
                    checkbox_array.forEach { it.isChecked = false }
                } else if (isChecked) {
                    checkbox_array.forEach { it.isChecked = true }
                }
                viewBinding.checkBox1.text = if(isChecked) "Select None" else "Select All"
                viewBinding.checkBox1.setCycle(it.sephiroth.android.library.checkbox3state.R.array.sephiroth_checkbox3_cycleCheckedUncheckedOnly)
                listenToUpdates = true
            }
        }

        viewBinding.getCheckStateInt.setOnClickListener {
            val currentStateInt = viewBinding.threeStateSingleCheckBox.checkState
            Toast.makeText(this, "current state Int $currentStateInt", Toast.LENGTH_SHORT).show()
        }
        viewBinding.checkStateGuide.text = "${viewBinding.checkStateGuide.text} \n [Checked => 1, Unchecked => 0, Indeterminated => -1]"

    }

    override fun onContentChanged() {
        super.onContentChanged()

        checkbox_array = arrayOf(viewBinding.checkBox2, viewBinding.checkBox3, viewBinding.checkBox4)

        checkbox_array.forEach { checkBox->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (listenToUpdates) {
                    listenToUpdates = false
                    val checked_size = checkbox_array.filter { it.isChecked }.size

                    if (checked_size == checkbox_array.size) {
                        viewBinding.checkBox1.setCycle(it.sephiroth.android.library.checkbox3state.R.array.sephiroth_checkbox3_cycleCheckedUncheckedOnly)
                        viewBinding.checkBox1.setChecked(true, false)
                        viewBinding.checkBox1.text = "Select None"
                    } else if (checked_size == 0) {
                        viewBinding.checkBox1.setCycle(it.sephiroth.android.library.checkbox3state.R.array.sephiroth_checkbox3_cycleCheckedUncheckedOnly)
                        viewBinding.checkBox1.setChecked(false, false)
                        viewBinding.checkBox1.text = "Select All"
                    } else {
                        viewBinding.checkBox1.setCycle(it.sephiroth.android.library.checkbox3state.R.array.sephiroth_checkbox3_cycleAll)
                        viewBinding.checkBox1.setChecked(false, true)
                        viewBinding.checkBox1.text = "Select All"
                    }
                    listenToUpdates = true
                }
            }
        }

        viewBinding.threeStateSingleCheckBox.setOnNotifyListener { buttonView, isChecked, isIndetermianted ->
            Toast.makeText(this, "isIndetermianted $isIndetermianted \nisChecked $isChecked", Toast.LENGTH_SHORT).show()
        }

        viewBinding.apply {
            unchecked.setOnClickListener {
                viewBinding.threeStateSingleCheckBox.checkState = 0
            }
            checked.setOnClickListener {
                viewBinding.threeStateSingleCheckBox.checkState = 1
            }
            indeterminated.setOnClickListener {
                viewBinding.threeStateSingleCheckBox.checkState = -1
            }
        }
    }
}
