package com.schopenhauer.nous.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.schopenhauer.nous.R

val NotoSansKrMedium = FontFamily(
    Font(R.font.noto_sans_kr_medium)
)

val NotoSansKrRegular = FontFamily(
    Font(R.font.noto_sans_kr_regular)
)

val baseline = Typography()

val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = NotoSansKrRegular),
    displayMedium = baseline.displayMedium.copy(fontFamily = NotoSansKrRegular),
    displaySmall = baseline.displaySmall.copy(fontFamily = NotoSansKrRegular),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = NotoSansKrRegular),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = NotoSansKrRegular),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = NotoSansKrRegular),
    titleLarge = baseline.titleLarge.copy(fontFamily = NotoSansKrMedium),
    titleMedium = baseline.titleMedium.copy(fontFamily = NotoSansKrMedium),
    titleSmall = baseline.titleSmall.copy(fontFamily = NotoSansKrMedium),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = NotoSansKrRegular),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = NotoSansKrRegular),
    bodySmall = baseline.bodySmall.copy(fontFamily = NotoSansKrRegular),
    labelLarge = baseline.labelLarge.copy(fontFamily = NotoSansKrRegular),
    labelMedium = baseline.labelMedium.copy(fontFamily = NotoSansKrRegular,),
    labelSmall = baseline.labelSmall.copy(fontFamily = NotoSansKrRegular),
)

