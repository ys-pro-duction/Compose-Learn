package com.yogesh.composelearn.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
@Preview(showBackground = true)
fun ConstraintLayoutUse(modifier: Modifier = Modifier) {
    val constraintSet = ConstraintSet {
        val redBox = this.createRefFor("redBox")
        val blueBox = this.createRefFor("blueBox")
        val (item1, item2, item3, item4) = createRefsFor("item1", "item2", "item3", "item4")
        createHorizontalChain(item1, item2, item3, item4)
        createVerticalChain(item3, item4,item1, item2, chainStyle = ChainStyle.Spread)
        constrain(redBox) {
            linkTo(parent.start, parent.top, blueBox.start, blueBox.top)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
//            centerTo(parent)

        }
        constrain(blueBox) {
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        createHorizontalChain(redBox, blueBox, chainStyle = ChainStyle.Packed)
        createVerticalChain(redBox, blueBox, chainStyle = ChainStyle.Spread)

    }
    ConstraintLayout(
        modifier = modifier
            .padding(8.dp)
            .background(Color(0xFFDDDDDD))
            .fillMaxSize(),
        constraintSet = constraintSet
    ) {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redBox"),
            contentAlignment = Alignment.Center
        ) {
            Text("RED")
        }
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .layoutId("blueBox"),
            contentAlignment = Alignment.Center
        ) {
            Text("BlUE", color = Color.White)
        }
        Text("Item 4", modifier = Modifier.layoutId("item4"))
        Text("Item 2", modifier = Modifier.layoutId("item2"))
        Text("Item 1", modifier = Modifier.layoutId("item1"))
        Text("Item 3", modifier = Modifier.layoutId("item3"))


    }
}
@Preview(showBackground = true)
@Composable
fun BarrierGuideLineExample() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (text1, text2, button, line, line2) = createRefs()
        val barrier = createEndBarrier(text1, text2, margin = 12.dp)
        val guideline = createGuidelineFromTop(0.3f)

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Red).constrainAs(text1) {
                    top.linkTo(guideline, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("RED", color = Color.White)
        }
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue).constrainAs(text2) {
                top.linkTo(text1.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 32.dp)
            },
            contentAlignment = Alignment.Center
        ) {
            Text("BlUE", color = Color.White)
        }

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.size(100.dp).constrainAs(button) {
                start.linkTo(barrier, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Next")
        }
        Box (modifier = Modifier.fillMaxHeight().width(1.dp).background(Color.Black)
            .constrainAs(line){
                start.linkTo(barrier)
            }){
        }
        Box (modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black)
            .constrainAs(line2){
                top.linkTo(guideline)
            }){
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormLayout() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (nameLabel, nameField, emailLabel, emailField, submitButton) = createRefs()
        val labelBarrier = createEndBarrier(nameLabel, emailLabel)

        // Name label
        Text(
            text = "Name:",
            modifier = Modifier.constrainAs(nameLabel) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        // Name text field
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.constrainAs(nameField) {
                start.linkTo(labelBarrier, margin = 8.dp)
                top.linkTo(nameLabel.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        // Email label
        Text(
            text = "Email:",
            modifier = Modifier.constrainAs(emailLabel) {
                top.linkTo(nameField.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )

        // Email text field
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.constrainAs(emailField) {
                start.linkTo(labelBarrier, margin = 8.dp)
                top.linkTo(emailLabel.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        // Submit button
        Button(
            onClick = { /* Submit action */ },
            modifier = Modifier.constrainAs(submitButton) {
                top.linkTo(emailField.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text("Submit")
        }

    }
}