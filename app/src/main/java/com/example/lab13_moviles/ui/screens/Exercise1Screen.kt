package com.example.lab13_moviles.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_moviles.ui.theme.Lab13movilesTheme

/**
 * Tipos de transición configurables para demostrar las capacidades de AnimatedVisibility.
 */
enum class VisibilityTransitionType(val label: String) {
    FADE_ONLY("Fade In / Out"),
    FADE_AND_EXPAND("Fade + Expand / Shrink"),
    SLIDE_AND_FADE("Slide + Fade"),
    SCALE_AND_FADE("Scale + Fade")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Exercise1Screen(
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }
    var selectedTransition by remember { mutableStateOf(VisibilityTransitionType.FADE_ONLY) }
    var animationDurationMs by remember { mutableFloatStateOf(600f) }
    val durationInt = animationDurationMs.toInt()

    // Configuramos las transiciones de entrada y salida dinámicas según la selección
    val enterTransition = remember(selectedTransition, durationInt) {
        val animSpec = tween<Float>(durationMillis = durationInt)
        val intSizeSpec = tween<IntSize>(durationMillis = durationInt)
        val intOffsetSpec = tween<IntOffset>(durationMillis = durationInt)
        when (selectedTransition) {
            VisibilityTransitionType.FADE_ONLY -> fadeIn(animationSpec = animSpec)
            VisibilityTransitionType.FADE_AND_EXPAND -> fadeIn(animationSpec = animSpec) + expandVertically(animationSpec = intSizeSpec)
            VisibilityTransitionType.SLIDE_AND_FADE -> slideInVertically(
                initialOffsetY = { -it / 2 },
                animationSpec = intOffsetSpec
            ) + fadeIn(animationSpec = animSpec)
            VisibilityTransitionType.SCALE_AND_FADE -> scaleIn(animationSpec = animSpec) + fadeIn(animationSpec = animSpec)
        }
    }

    val exitTransition = remember(selectedTransition, durationInt) {
        val animSpec = tween<Float>(durationMillis = durationInt)
        val intSizeSpec = tween<IntSize>(durationMillis = durationInt)
        val intOffsetSpec = tween<IntOffset>(durationMillis = durationInt)
        when (selectedTransition) {
            VisibilityTransitionType.FADE_ONLY -> fadeOut(animationSpec = animSpec)
            VisibilityTransitionType.FADE_AND_EXPAND -> fadeOut(animationSpec = animSpec) + shrinkVertically(animationSpec = intSizeSpec)
            VisibilityTransitionType.SLIDE_AND_FADE -> slideOutVertically(
                targetOffsetY = { -it / 2 },
                animationSpec = intOffsetSpec
            ) + fadeOut(animationSpec = animSpec)
            VisibilityTransitionType.SCALE_AND_FADE -> scaleOut(animationSpec = animSpec) + fadeOut(animationSpec = animSpec)
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Laboratorio 13 - Ejercicio 1",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Animación con AnimatedVisibility",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Alterna la visibilidad de un elemento componible con animaciones suaves de entrada (enter) y salida (exit).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                )
            }
        }

        // Panel de control: Botón de alternancia de visibilidad
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { isVisible = !isVisible },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isVisible) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(50.dp)
                ) {
                    Text(
                        text = if (isVisible) "Ocultar Cuadro de Color" else "Mostrar Cuadro de Color",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (isVisible) Color(0xFF4CAF50) else Color(0xFFF44336))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isVisible) "Estado: Visible (Activo)" else "Estado: Oculto",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Selector de Efectos y Duración
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Efecto de Transición:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    VisibilityTransitionType.values().forEach { type ->
                        FilterChip(
                            selected = (selectedTransition == type),
                            onClick = { selectedTransition = type },
                            label = { Text(type.label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Duración de animación:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${durationInt} ms",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Slider(
                    value = animationDurationMs,
                    onValueChange = { animationDurationMs = it },
                    valueRange = 200f..1500f,
                    steps = 12
                )
            }
        }

        // Zona del elemento animado con AnimatedVisibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (!isVisible) {
                Text(
                    text = "El cuadro está oculto.\nHaz clic en el botón para mostrarlo suavemente.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }

            // Llamada directa usando AnimatedVisibility en ColumnScope
            this@Column.AnimatedVisibility(
                visible = isVisible,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 250.dp, height = 170.dp)
                        .shadow(elevation = 12.dp, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF3F51B5),
                                    Color(0xFF009688)
                                )
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.25f),
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "★",
                                    fontSize = 26.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Cuadro Animado",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Efecto: ${selectedTransition.label}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // Sección de Respuestas a la Pregunta de la Rúbrica
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "i",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pregunta de Evaluación (Laboratorio):",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "¿Qué ventaja observas al utilizar AnimatedVisibility en lugar de simplemente cambiar la visibilidad del elemento? ¿Cómo afecta el flujo de la interfaz?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Continuidad Visual y UX: Elimina cortes bruscos ('layout jumps') al insertar o remover elementos del árbol gráfico, guiando la atención del usuario de manera fluida.\n" +
                           "• Coordinación de Layout Automática: AnimatedVisibility mide y anima dinámicamente el espacio ocupado (expand/shrink), evitando que los demás elementos colapsen o salten repentinamente.\n" +
                           "• Ciclo de Vida Controlado: Mantiene temporalmente el composable en el composition tree durante la fase de salida (exit) hasta que la animación concluye, garantizando una liberación de recursos ordenada.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Exercise1ScreenPreview() {
    Lab13movilesTheme {
        Exercise1Screen()
    }
}
