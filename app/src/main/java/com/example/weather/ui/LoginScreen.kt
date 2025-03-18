package com.example.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onNavigateToWeather: () -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("130****8888") }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 返回按钮和帮助文本
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* 返回操作 */ },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "返回",
                    tint = Color(0xFF666666)
                )
            }
            Text(
                text = "帮助",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF666666),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 吉祥物图片
        Image(
            painter = painterResource(id = R.drawable.mascot),
            contentDescription = "吉祥物",
            modifier = Modifier
                .size(140.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 欢迎文本
        Text(
            text = "玩Hello，交朋友",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF333333)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 手机号和更换按钮
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = phoneNumber,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            // 更换按钮
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
                    .clickable { /* 切换手机号操作 */ }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "更换",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF666666),
                        fontSize = 13.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 认证服务说明
        Text(
            text = "认证服务由移动一认提供",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF999999),
                fontSize = 12.sp
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // 登录按钮
        Button(
            onClick = { 
                if (isChecked) {
                    onNavigateToWeather()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C4DFF),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFBBBBBB)
            ),
            enabled = isChecked,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = "一键登录",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 用户协议勾选
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 20.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(if (isChecked) Color(0xFF7C4DFF) else Color.White)
                    .clickable { isChecked = !isChecked }
                    .border(1.dp, if (isChecked) Color(0xFF7C4DFF) else Color(0xFFCCCCCC), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = Color(0xFF999999),
                        fontSize = 12.sp
                    )) {
                        append("我已同意并阅读")
                    }
                    withStyle(style = SpanStyle(
                        color = Color(0xFF7C4DFF),
                        fontSize = 12.sp
                    )) {
                        append("《铁通统一认证服务条款》")
                    }
                },
                modifier = Modifier.clickable { isChecked = !isChecked }
            )
        }
    }
} 