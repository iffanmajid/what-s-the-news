package com.whatsthenews.utils

import com.whatsthenews.model.News

object MockUtil {

    fun mockNews() = News(
            "Tak Efektif Varian Baru COVID-19, Afrika Selatan Tangguhkan Penggunaan Vaksin AstraZeneca - Majalah Farmasetika",
            "Majalah Farmasetika - Afrika Selatan telah menangguhkan penggunaan vaksin penyakit coronavirus 2019 (COVID-19) Oxford dan AstraZeneca, setelah temuan itu menunjukkan perlindungan minimal terhadap infe",
            "https://i0.wp.com/farmasetika.com/wp-content/uploads/2020/12/close-up-hand-holding-covid-vaccine-scaled.jpg?fit=2048%2C1365&;amp;ssl=1",
            "https://farmasetika.com/2021/02/09/tak-efektif-varian-baru-covid-19-afrika-selatan-tangguhkan-penggunaan-vaksin-astrazeneca/")
    fun mockNewsList() = listOf(mockNews())
}