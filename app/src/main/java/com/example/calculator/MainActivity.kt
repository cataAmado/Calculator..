package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.colorResource
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorLayout()
                }
            }
        }
    }
}

@Composable
fun CalculatorLayout() {
    var operation by remember {mutableStateOf("")}
    var total by remember { mutableStateOf("") }

    // Crear la disposición de los botones de números y operadores
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = operation, // Aquí se visualiza la operación
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.End
        )
        // Pantalla para mostrar resultados y números
        Text(
            text = total, // Aquí puedes enlazar el estado para mostrar los números y resultados
            fontSize = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.buttonPadding)))
        // Línea adicional (AC, borrar, %, /)
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.buttonPadding))
        ) {
            CalculatorButton(text = "AC", color = colorResource(id = R.color.purple)) {
                if (operation.isNotEmpty()) {
                    operation = operation.dropLast(1) // Eliminar el último carácter
                    updateResult(operation) { total = it }
                }
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "C", color = colorResource(id = R.color.red)){
                operation = ""
                total = ""
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "%", color = colorResource(id = R.color.orange)){
                if(isExpressionValid(operation)){
                    operation += "%"
                    updateResult(operation,{total = it})
                }
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "/", color = colorResource(id = R.color.orange)){
                if(isExpressionValid(operation)){
                    operation += "/"
                    updateResult(operation,{total = it})
                }
            }
        }
        // Primera fila (7, 8, 9, *)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.buttonPadding)))
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.buttonPadding))
        ) {
            CalculatorButton(text = "7"){
                operation += "7"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "8"){
                operation += "8"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "9"){
                operation += "9"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "*", color = colorResource(id = R.color.orange)){
                if(isExpressionValid(operation)){
                    operation += "*"
                    updateResult(operation,{total = it})
                }
            }
        }
        // Segunda fila (4, 5, 6, -)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.buttonPadding)))
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.buttonPadding))
        ) {
            CalculatorButton(text = "4"){
                operation += "4"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "5"){
                operation += "5"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "6"){
                operation += "6"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "-", color = colorResource(id = R.color.orange)){
                if(isExpressionValid(operation)){
                    operation += "-"
                    updateResult(operation,{total = it})
                }
            }
        }
        // Tercera fila (1, 2, 3, +)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.buttonPadding)))
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.buttonPadding))
        ) {
            CalculatorButton(text = "1"){
                operation += "1"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "2"){
                operation += "2"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "3"){
                operation += "3"
                updateResult(operation,{total = it})
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))
            CalculatorButton(text = "+", color = colorResource(id = R.color.orange)){
                if(isExpressionValid(operation)){
                    operation += "+"
                    updateResult(operation,{total = it})
                }
            }
        }
        // Cuarta fila (0, =, +)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.buttonPadding)))
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.buttonPadding))
        ) {
            // Botón de 0 más ancho
            Button(
                onClick = {
                    operation += "0"
                    updateResult(operation) { total = it }
                },
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.zeroWitdth)) // Usar el ancho personalizado
                    .height(dimensionResource(id = R.dimen.buttonHeight)),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonBackground)),
                shape = CircleShape
            ) {
                Text(
                    text = "0",
                    fontSize = dimensionResource(id = R.dimen.buttonTextSize).value.sp,
                    color = colorResource(id = R.color.buttonText)
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))

            // Botón de "."
            CalculatorButton(text = ".", color = colorResource(id = R.color.purple)) {
                if (!operation.contains(".")) {
                    operation += "."
                    updateResult(operation) { total = it }
                }
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.buttonPadding)))

            // Botón de "="
            CalculatorButton(text = "=", color = colorResource(id = R.color.orange)){
                try {
                    val expr = ExpressionBuilder(operation).build() // Evalúa la expresión
                    total = expr.evaluate().toString()
                } catch (e: Exception) {
                    total = "Error"
                }
            }
        }
    }
}


fun updateResult(expression: String, updateResult: (String) -> Unit) {
    // Solo evaluar si hay al menos un número en la expresión
    if (expression.isNotEmpty()) {
        try {
            // Interceptar el símbolo de porcentaje (%) y convertirlo en una multiplicación por 0.01
            val expressionWithPercentage = expression.replace(Regex("(\\d+)%")) { matchResult ->
                val number = matchResult.groupValues[1] // Capturar el número antes del %
                "($number * 0.01)" // Convertir 10% en (10 * 0.01)
            }

            val expr = ExpressionBuilder(expressionWithPercentage).build()
            val evaluationResult = expr.evaluate().toString()
            val formattedResult = formatResult(evaluationResult)
            updateResult(formattedResult)
        } catch (e: Exception) {
            updateResult("") // Mostrar "Error" si algo sale mal
        }
    } else {
        updateResult("") // Resultado vacío si no hay expresión
    }
}

fun isExpressionValid(expression: String): Boolean {
    // Comprueba si la expresión es válida para ser evaluada
    // Verificar que no termine en un operador o que no contenga operadores consecutivos
    if (expression.isEmpty() || expression.last() in setOf('+', '-', '*', '/')) {
        return false
    }

    // Verificar si hay operadores consecutivos
    val operators = setOf('+', '-', '*', '/')
    for (i in 1 until expression.length) {
        if (expression[i] in operators && expression[i - 1] in operators) {
            return false
        }
    }
    return true
}

fun formatResult(result: String): String {
    return try {
        // Formatear el número con comas como separadores de miles
        val number = result.toDouble()
        val formattedResult = String.format("%,d", number.toLong()) // Formato para enteros
        if (number % 1 != 0.0) {
            // Si es un número decimal, agregar el decimal
            val decimalPart = number.toString().substringAfter(".")
            "$formattedResult.$decimalPart" // Combina la parte entera y decimal
        } else {
            formattedResult
        }
    } catch (e: Exception) {
        "Error" // Si no se puede formatear, devuelve "Error"
    }
}

@Composable
fun CalculatorButton(text: String, color: Color = colorResource(id = R.color.buttonBackground), onClick: () -> Unit) {
    Button(
        onClick = onClick, // Aquí acepta la acción onClick
        modifier = Modifier
            .size(
                width = dimensionResource(id = R.dimen.buttonWidth),
                height = dimensionResource(id = R.dimen.buttonHeight)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = CircleShape
    ) {
        Text(
            text = text,
            fontSize = dimensionResource(id = R.dimen.buttonTextSize).value.sp,
            color = colorResource(id = R.color.buttonText)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        CalculatorLayout()
    }
}