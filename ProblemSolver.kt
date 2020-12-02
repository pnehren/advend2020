import java.io.File
import java.io.BufferedReader

fun main(args:Array<String>) {
    val input = File("input_1-1.txt").readText().replace("\n","").split(",").map { it.toInt() }

    firstDecember1(input)
    firstDecember2(input)
    secondDecember1()
}

fun firstDecember1(input: List<Int>){    
     input.forEachIndexed{index, first->
        input.subList(index,input.size).forEach{second->
            if(first+second == 2020)
              println("01.12-1 Match $first $second multiplied: ${first*second}")
        }
    }
}

fun firstDecember2(input: List<Int>){
     input.forEachIndexed{index1,first->
        input.subList(index1,input.size).forEachIndexed{index2, second->
            input.subList(index2,input.size).forEach{third->
                if(first+second+third == 2020)
              		println("01.12-2 Match $first $second $third multiplied: ${first*second*third}")
            }
            
        }
    }
}
fun secondDecember1(){

    val bufferedReader = File("input_2-1.txt").bufferedReader()
    val lineList = mutableListOf<String>()
  
    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it.replace("\\","")) } }
    
    val ruleLst = lineList.map{line-> 
        val rule = Rule(line.split("-").first().toInt(),line.split("-")[1].takeWhile{it.isDigit()}.toInt(),line.first{it-> it.isLetter()},line.takeLastWhile{it-> it.isLetter()})
        val count = rule.pwd.split(rule.char).size-1
        rule.valid = count in rule.min..rule.max
        
        val array = rule.pwd.toCharArray()

        val firstChar = array[rule.min-1] == rule.char 
        val secondChar = array[rule.max-1] == rule.char
        rule.valid2 = firstChar && !secondChar || !firstChar && secondChar 
        
        //println(rule)
        rule
    }
    println("02.12-1 size: ${ruleLst.size} valid1: ${ruleLst.filter{it.valid}.size} valid2: ${ruleLst.filter{it.valid2}.size}")


}

data class Rule(val min:Int, val max: Int, val char:Char, val pwd: String){
    var valid = false
    var valid2 = false
}
