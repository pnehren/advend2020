import java.io.File
import java.io.BufferedReader
const val DEBUG_DETAIL:Boolean = false

fun main() {
    val input = File("input_1-1.txt").readText().replace("\n","").split(",").map { it.toInt() }

    december01_1(input)
    december01_2(input)
    december02()
    december03()
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

fun december03(){
    val bufferedReader = File("input_3-1.txt").bufferedReader()
    val lineList = mutableListOf<String>()
    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it.replace("\\","")) } }
    val v1 = december03_2(1,1,lineList).toLong()
    val v2 = december03_2(3,1,lineList).toLong()
    val v3 = december03_2(5,1,lineList).toLong()
    val v4 = december03_2(7,1,lineList).toLong()
    val v5 = december03_2(1,2,lineList).toLong()
    val multiplied:Long = (v1*v2*v3*v4*v5)
    println("03.12. multiplied: $multiplied")
}

fun december03_2(right: Int = 3, down: Int = 1, lineList:List<String>):Int{
    var countTrees = 0
    var index = 0

    lineList.forEachIndexed{ i, line ->
        val lineArray = line.toCharArray()

        if(i>0 && i%down==0){
            index++
            val lineIndex = (index*right)%line.length
            if(lineArray[lineIndex]=='#'){
                countTrees++
                lineArray[lineIndex] = 'X'
            }else{
                lineArray[lineIndex] = '0'
            }
        }
        if(DEBUG_DETAIL)
            println(String(lineArray))
    }
    println("03.12. right: $right down: $down trees hit: $countTrees")
    return countTrees

}

data class Rule(val min:Int, val max: Int, val char:Char, val pwd: String){
    var validWay1 = false
    var validWay2 = false
}
