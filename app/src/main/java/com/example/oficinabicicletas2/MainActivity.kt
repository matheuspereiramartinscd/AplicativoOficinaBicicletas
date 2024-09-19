package com.example.oficinabicicletas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                HomeScreen()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedDate by remember { mutableStateOf("Nenhuma data selecionada") }
    var selectedService by remember { mutableStateOf("Nenhum serviço selecionado") }
    var stockItem by remember { mutableStateOf("") }
    var showAgendamentoDialog by remember { mutableStateOf(false) }
    var showServicoDialog by remember { mutableStateOf(false) }
    var showEstoqueDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Bem-vindo à Oficina de Bicicletas", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Data do Agendamento: $selectedDate")
        Text("Serviço Selecionado: $selectedService")
        Text("Item no Estoque: $stockItem")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { showAgendamentoDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Agendamentos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showServicoDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Serviços")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showEstoqueDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Estoque de Peças")
        }

        // Diálogos
        if (showAgendamentoDialog) {
            AgendamentoDialog { date ->
                selectedDate = date
                showAgendamentoDialog = false
            }
        }

        if (showServicoDialog) {
            ServicoDialog { service ->
                selectedService = service
                showServicoDialog = false
            }
        }

        if (showEstoqueDialog) {
            EstoqueDialog { item ->
                stockItem = item
                showEstoqueDialog = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendamentoDialog(onDateSelected: (String) -> Unit) {
    var selectedDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDateSelected(selectedDate) },
        title = { Text("Agendamentos") },
        text = {
            Column {
                Text("Selecione uma data (YYYY-MM-DD):")
                TextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("Data") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text("Fechar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicoDialog(onServiceSelected: (String) -> Unit) {
    val services = listOf("Serviço 1", "Serviço 2", "Serviço 3")
    var selectedService by remember { mutableStateOf(services[0]) }

    AlertDialog(
        onDismissRequest = { onServiceSelected("") },
        title = { Text("Serviços") },
        text = {
            Column {
                Text("Escolha um serviço:")
                services.forEach { service ->
                    Row {
                        RadioButton(
                            selected = (selectedService == service),
                            onClick = { selectedService = service }
                        )
                        Text(service)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onServiceSelected(selectedService) }) {
                Text("Fechar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstoqueDialog(onItemAdded: (String) -> Unit) {
    var newItem by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onItemAdded("") },
        title = { Text("Estoque de Peças") },
        text = {
            Column {
                Text("Adicionar item ao estoque:")
                TextField(
                    value = newItem,
                    onValueChange = { newItem = it },
                    label = { Text("Nome do item") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onItemAdded(newItem)
                newItem = ""
            }) {
                Text("Adicionar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        HomeScreen()
    }
}
