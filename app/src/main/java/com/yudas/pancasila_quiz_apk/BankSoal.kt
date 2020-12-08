package com.yudas.pancasila_quiz_apk

class BankSoal {

    private var soal     : ArrayList<ArrayList<String?>> = arrayListOf()
    private  val jawaban : ArrayList<ArrayList<String?>> = arrayListOf()
    private var jawabanBenar : ArrayList<String?> = arrayListOf()

    fun setSoal(isiSoal: ArrayList<String?>){
        soal.add(isiSoal)
    }

    fun setJawabanBenar(jwbBenar: String?){
        jawabanBenar.add(jwbBenar)
    }

    fun setJawaban(jwb: ArrayList<String?>){
        jawaban.add(jwb)
    }


    fun soalLength(): Int{
        return soal.size;
    }


    fun getSoal(i : Int): ArrayList<String?>{
        return soal[i]
    }

    fun getJawaban(i : Int) : ArrayList<String?> {
        return jawaban[i]
    }

    fun getJawabanBenar(i: Int): String? {
        return jawabanBenar[i]
    }
}
