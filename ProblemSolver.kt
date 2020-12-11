import java.io.File
import java.io.BufferedReader
const val DEBUG_DETAIL:Boolean = false

fun main() {
    /*
    val input = File("input_1-1.txt").readText().replace("\n","").split(",").map { it.toInt() }
    december01_1(input)
    december01_2(input)
    december02()
    december03()
    december04()
    december05()
    december06()
    december08()
    december09()
    */
    december10()
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
    data class Rule(val min:Int, val max: Int, val char:Char, val pwd: String){
        var validWay1 = false
        var validWay2 = false
    }

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

fun december04(){
    data class Passport( val byr:String, val iyr:String, val eyr:String, val hgt:String, val hcl:String, val ecl:String, val pid:String, val cid:String?){}

    val validPassports=mutableListOf<Passport>()
    File("input_4-1.txt").bufferedReader().use { it.readLines().joinToString().replace(", ,","\n")}.split("\n").forEach { 
        val passMap = (it.trim()).split(" ").map { it.split(":")[0] to it.split(":")[1] }.toMap()
        try{ validPassports.add(
                Passport(passMap.get("byr")!!,passMap.get("iyr")!!,passMap.get("eyr")!!,passMap.get("hgt")!!,passMap.get("hcl")!!,passMap.get("ecl")!!,passMap.get("pid")!!,passMap.get("cid"))
            )
        }catch(e:Exception){} 
     }
    println("04.12. Passports: ${validPassports.size}")
}

fun december05(){
    data class Seat(val row:Int, val column:Int, val seatId:Int){}
    val seats=mutableListOf<Seat>()
    File("input_5.txt").bufferedReader().use { 
        it.readLines().forEach { line->
            var row = 128
            var col = 8
            line.toCharArray().forEachIndexed {charIndex, char->
                if(charIndex<7){
                    val diff = Math.pow(2.0,6.0-(charIndex)).toInt()
                    row = if(char=='B') + diff else - diff
                }
                if(charIndex>6){
                    val diff = Math.pow(2.0,3.0-(charIndex-6)).toInt()
                    col = if(char=='R') + diff else -diff
                }
            }
            seats.add(Seat(row/2, col/2,  row/2*8+col/2))
        }
    }
    println("05.12. highest: ${seats.maxByOrNull { it.seatId }}")
    seats.filter{ seat ->
        val before: Seat? = seats.firstOrNull{it.seatId == seat.seatId-1}
        val after: Seat? = seats.firstOrNull{it.seatId == seat.seatId+1}
        before == null || after == null
    }.forEach { 
        println(it.seatId)
     }
}

fun december06(){
    //TODO
}

fun december07(){
    //TODO
}

fun december08(){
    data class BootSequence(val proc: String, val seqVal:Int, var count:Int=0){}
    var acc = 0
    var index = 0
    var finished = false
    val boot=mutableListOf<BootSequence>()
    File("input_8.txt").bufferedReader().use { 
        it.readLines().forEach { line->
            line.split(" ").apply { 
                boot.add(BootSequence(this.first(),this[1].toInt()))
             }
        }
    }
    while(!finished){
        val currentBootSeq = boot[index]
        println("i: $index c: $currentBootSeq acc: $acc")
        if(currentBootSeq.count>0)
            finished =true
        if(!finished) when(currentBootSeq.proc){
            "nop" -> index++
            "acc" -> { 
                acc += currentBootSeq.seqVal
                index++ 
            }
            "jmp" -> index += currentBootSeq.seqVal
            else ->{}
        }
        currentBootSeq.count ++
    }
}


data class Number(val value:Long){
    var valid = false
}
val numbers = mutableListOf<Number>()
fun december09(){
    File("input_9.txt").bufferedReader().use { 
        it.readLines().forEach { line->
            val currentNumber = Number(line.toLong())
            numbers.add(currentNumber)
            val sublist = if(numbers.size>25)
                numbers.subList(numbers.size-26, numbers.size-1)
                else { 
                    currentNumber.valid = true
                    return@forEach
                }
            sublist.forEachIndexed{index, first->
                sublist.subList(index,sublist.size).forEach{second->
                    if(first.value != second.value && first.value+second.value == currentNumber.value)
                        currentNumber.valid = true
                }
            }

        }
    }
    val notValidValue = numbers.firstOrNull{!it.valid}?.value ?: -1
    println("First not valid: ${notValidValue}")

    numbers.forEach { 
        if(it.value != notValidValue)
        recursiveSum(it.value,numbers.indexOf(it),numbers.indexOf(it)+1,notValidValue)
     }
}

fun recursiveSum(sum:Long, from: Int, to:Int, lookFor:Long):Long{
    if(sum>lookFor || to > numbers.size) return 0
    val tempSum = sum+numbers[to].value
    if(tempSum == lookFor){
        val multiply = numbers[from].value+numbers[to].value
        println("Match. sum: $sum from: $from to: $to m: $multiply")
        return 0
    }
    else return recursiveSum(tempSum,from, to+1,lookFor)   
}

fun december10(){
    val numbers = File("input_10.txt").bufferedReader().use { 
        it.readLines().map { line->
            line.toInt()
        }
    }.sorted()
    var index = 0
    var count1 = 0
    var count3 = 0
    val end = numbers.maxOrNull() ?: 0
    var finished = false
    while(!finished){
        if(index==end){
            count3++
            finished = true
        }
        else{
            if(numbers.find { it==index+1 } !=null){
                index++
                count1++
            }else{
                if(numbers.find { it==index+3 } !=null){
                    index+=3
                    count3++
                }
            }
        }
    }
    println("10.12. c1: $count1 c2: $count3 multiplied: ${count1*count3}")
    println("10.12. total: ${totalCombinations(numbers)}")
}

/* fun recursiveSum2(index: Int,lookFor:Int, list:List<Int>):Long{
    if(index==lookFor) return 1
    if(list.find { it==index }==null && index != 0)
        return 0
    else{
        return recursiveSum2(index+1,lookFor,list)+recursiveSum2(index+2,lookFor,list)+recursiveSum2(index+3,lookFor,list)
    }
} */
//Inspired by NG
fun totalCombinations (adapters:List<Int>):Long {
    val N = adapters [adapters.size-1]; // Ausgabe des letzten Adapters; das wird dann vom Gerät selbst noch +3 erhöht
    assert (N >= adapters.size);
    val combinations = Array(N+3){0L}
    combinations [0] = 0; combinations [1] = 0; combinations [2] = 1;
    var k = 0;

    for (i in 0 ..N-1) {
      assert (adapters [k] >= i+1);
      if (adapters [k] == i+1) {
        ++k;
        combinations [i+3] = combinations [i+2]+combinations [i+1]+combinations [i];
      } else
        combinations [i+3] = 0;
    }
    return combinations [N+2];
  }