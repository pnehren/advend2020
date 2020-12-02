import java.io.File
import java.io.BufferedReader

fun main() {
    val input = File("input_1-1.txt").readText().replace("\n","").split(",").map { it.toInt() }

    december01_1(input)
    december01_2(input)
    december02()
}

fun december01_1(input: List<Int>){    
     input.forEachIndexed{index, first->
        input.subList(index,input.size).forEach{second->
            if(first+second == 2020)
              println("01.12-1 Match $first $second multiplied: ${first*second}")
        }
    }
}

fun december01_2(input: List<Int>){
     input.forEachIndexed{index1,first->
        input.subList(index1,input.size).forEachIndexed{index2, second->
            input.subList(index2,input.size).forEach{third->
                if(first+second+third == 2020)
              		println("01.12-2 Match $first $second $third multiplied: ${first*second*third}")
            }
            
        }
    }
}
fun december02(){

    val bufferedReader = File("input_2-1.txt").bufferedReader()
    val lineList = mutableListOf<String>()
  
    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it.replace("\\","")) } }
    
    val ruleLst = lineList.map{line-> 
        val passwordRule = Rule(line.split("-").first().toInt(),line.split("-")[1].takeWhile{it.isDigit()}.toInt(),line.first{it-> it.isLetter()},line.takeLastWhile{it-> it.isLetter()})
        
        //check valid1
        val charCount = passwordRule.pwd.split(passwordRule.char).size-1
        passwordRule.validWay1 = charCount in passwordRule.min..passwordRule.max
        
        //check valid2
        val charArray = passwordRule.pwd.toCharArray()
        val firstChar = charArray[passwordRule.min-1] == passwordRule.char 
        val secondChar = charArray[passwordRule.max-1] == passwordRule.char
        passwordRule.validWay2 = firstChar && !secondChar || !firstChar && secondChar 
        passwordRule
    }
    println("02.12 Passwords valid1: ${ruleLst.filter{it.validWay1}.size} valid2: ${ruleLst.filter{it.validWay2}.size}")


}

data class Rule(val min:Int, val max: Int, val char:Char, val pwd: String){
    var validWay1 = false
    var validWay2 = false
}
