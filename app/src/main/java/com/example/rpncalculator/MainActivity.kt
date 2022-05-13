package com.example.rpncalculator

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.rpncalculator.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var stack: ArrayDeque<ListItemModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var input: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        this.recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        this.recyclerView.layoutManager = layoutManager

        this.stack = ArrayDeque()

        val adapter = ListAdapter(stack)
        this.recyclerView.adapter = adapter

        this.recyclerView.scrollToPosition(0)

        this.input = findViewById<TextView>(R.id.inputTextView)

        for (i in 0..9) {
            findViewById<Button>(
                resources.getIdentifier(
                    "buttonKey$i", "id",
                    this.packageName
                )
            ).setOnClickListener {
                this.inputKey(i.toString())
            }
        }

        findViewById<Button>(R.id.buttonDot).setOnClickListener {
            this.inputKey(".")
        }

        findViewById<Button>(R.id.buttonEnter).setOnClickListener {
            this.enter()
        }

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            this.genericOperation { item1, item2 -> item1+item2 }
        }

        findViewById<Button>(R.id.buttonSubtract).setOnClickListener {
            this.genericOperation { item1, item2 -> item1-item2 }
        }

        findViewById<Button>(R.id.buttonMultiply).setOnClickListener {
            this.genericOperation { item1, item2 -> item1-item2 }
        }

        findViewById<Button>(R.id.buttonDivide).setOnClickListener {
            this.genericOperation { item1, item2 -> item1/item2 }
        }

        findViewById<Button>(R.id.buttonPower).setOnClickListener {
            this.genericOperation { item1, item2 -> item1.pow(item2) }
        }

        findViewById<Button>(R.id.buttonRoot).setOnClickListener {
            this.genericOperation { item1, item2 -> item1.pow(1.0/item2) }
        }

        findViewById<Button>(R.id.buttonSwap).setOnClickListener {
            this.swap()
        }

        findViewById<Button>(R.id.buttonDrop).setOnClickListener {
            this.drop()
        }

        findViewById<Button>(R.id.buttonChangeSign).setOnClickListener {
            this.changeSign()
        }

        findViewById<Button>(R.id.buttonBackspace).setOnClickListener {
            this.backspace()
        }

        findViewById<Button>(R.id.buttonAC).setOnClickListener {
            this.ac()
        }

        findViewById<Button>(R.id.buttonSettings).setOnClickListener {
            val i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }


    }


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    private fun inputKey(value: String) {
        this.input.visibility = View.VISIBLE
        this.input.text = input.text.toString() + value
        this.recyclerView.scrollToPosition(0)
    }

    private fun enter() {
        val input = findViewById<TextView>(R.id.inputTextView)
        this.stack.addFirst(ListItemModel(input.text.toString().toDouble()))
        this.recyclerView.adapter!!.notifyDataSetChanged()
        this.input.text = ""
        this.input.visibility = View.GONE
    }

    private fun genericOperation(operation: (Double, Double) -> Double) {
        if(stack.size < 2) return
        val item2 = stack.removeFirst().value
        val item1 = stack.removeFirst().value
        val result: Double = operation(item1, item2)
        this.stack.addFirst(ListItemModel(result))
        this.recyclerView.adapter!!.notifyItemRemoved(1)
        this.recyclerView.adapter!!.notifyItemChanged(0)
    }

    private fun swap() {
        if(this.stack.size < 2) return
        Collections.swap(this.stack, 0, 1)
        this.recyclerView.adapter!!.notifyItemMoved(0,1)
        this.recyclerView.adapter!!.notifyItemRangeChanged(0, 2)
    }

    private fun drop() {
        if(this.stack.size < 1) return
        val input = findViewById<TextView>(R.id.inputTextView)
        input.text = this.stack.removeFirst().value.toString()
        input.visibility = View.VISIBLE
        this.recyclerView.adapter!!.notifyItemRemoved(0)
        this.recyclerView.adapter!!.notifyItemRangeChanged(0, this.recyclerView.adapter!!.itemCount)
    }

    private fun changeSign() {
        if(this.stack.size < 1) return
        this.stack[0].value *= -1
        this.recyclerView.adapter!!.notifyItemChanged(0)
    }

    private fun backspace() {
        if(this.input.text.isEmpty()) return
        this.input.text = this.input.text.dropLast(1)
    }

    private fun ac() {
        this.recyclerView.adapter!!.notifyItemRangeRemoved(0, this.recyclerView.adapter!!.itemCount)
        this.stack.clear()
    }
}