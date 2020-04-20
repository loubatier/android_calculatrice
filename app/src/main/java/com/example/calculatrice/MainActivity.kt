package com.example.calculatrice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initViews()
        println("start")
    }

    private fun initViews() {
        val parent:ConstraintLayout = findViewById<ConstraintLayout>(R.id.parentView)

        val buttons = parent.children.filter {
            it is Button
        }

        buttons.forEach {

            it.setOnClickListener {
                it as Button

                var value = it.text.toString()

                if (value == "=") {

                    val text = uiExpression.text.toString()
                    val expression = ExpressionBuilder(text).build()

                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) {
                        uiResult.text = longResult.toString()
                    } else {
                        uiResult.text = result.toString()
                    }

                } else if (value == "CLEAR") {

                    uiExpression.text = ""
                    uiResult.text = ""

                } else if (value == "DEL") {

                    val text = uiExpression.text.toString()
                    if(text.isNotEmpty()) {
                        uiExpression.text = text.drop(1)
                    }
                    uiExpression.text = ""

                } else {

                    evaluateExpression(it.text.toString(), clear = true)

                }
            }

        }
    }

    fun evaluateExpression(string: String, clear: Boolean) {
        if(clear) {
            uiResult.text = ""
            uiExpression.append(string)
        } else {
            uiExpression.append(uiResult.text)
            uiExpression.append(string)
            uiResult.text = ""
        }
    }

}
