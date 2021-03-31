import java.util.*
import kotlin.collections.ArrayList

class HuffmanTree(val head: Node) : Comparable<HuffmanTree> {
    class Node {
        var char = '\u0000'
        var value = 0
        var left: Node? = null
        var right: Node? = null
    }

    override fun compareTo(other: HuffmanTree): Int {
        return head.value - other.head.value
    }

    fun print() {
        println("char\tcount\tcode")
        fPrint(head, "")
    }

    fun getDictionary(): TreeMap<Char, String> {
        val map = TreeMap<Char, String>()
        fDictionary(head, "", map)
        return map
    }

    private fun fPrint(node: Node?, code: String) {
        if (node != null) {
            if (node.left == null) {
                if (code == "")
                    fPrint(node, "0")
                else
                    println("'${if (node.char == '\n') "\\n" else node.char}' \t${node.value}\t\t$code")
            } else {
                fPrint(node.left, code + "0")
                fPrint(node.right, code + "1")
            }
        }
    }

    private fun fDictionary(node: Node?, code: String, map: TreeMap<Char, String>) {
        if (node != null) {
            if (node.left == null) {
                if (code == "")
                    fDictionary(node, "0", map)
                else
                    map[node.char] = code
            } else {
                fDictionary(node.left, code + "0", map)
                fDictionary(node.right, code + "1", map)
            }
        }
    }
}

fun main() {
    val text =
        "Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference.\n" +
                "Kotlin is designed to interoperate fully with Java, and the JVM version of Kotlin's standard library\n" +
                "depends on the Java Class Library, but type inference allows its syntax to be more concise.\n" +
                "Kotlin mainly targets the JVM, but also compiles to JavaScript (e.g., for frontend web applications using React)\n" +
                "or native code (via LLVM); e.g., for native iOS apps sharing business logic with Android apps.\n" +
                "Language development costs are borne by JetBrains, while the Kotlin Foundation protects the Kotlin trademark.\n" +
                "On 7 May 2019, Google announced that the Kotlin programming language is now its preferred language for Android app developers.\n" +
                "As a result many developers have switched to Kotlin. Since the release of Android Studio 3.0 in October 2017,\n" +
                "Kotlin has been included as an alternative to the standard Java compiler.\n" +
                "The Android Kotlin compiler produces Java 6 bytecode by default (which runs in any later JVM),\n" +
                "but lets the programmer choose to target Java 8 up to 15, for optimization, or allows for more features,\n" +
                "e.g. Java 8 related with Kotlin 1.4, and has experimental record class support for Java 16 compatibility.\n" +
                "Kotlin support for JavaScript (i.e. classic back-end) is considered stable in Kotlin 1.3 by its developers,\n" +
                "while Kotlin/JS (IR-based) in version 1.4, is considered alpha. Kotlin/Native Runtime (for e.g. Apple support) is considered beta."
//    val text = "Hello world!"
//    val text = "aaaaaaaaaaaaaaabbbbbbbccccccddddddeeeee"



    val huffmanTree = makeHuffmanTree(text)
    val encodedText = encodeText(text, huffmanTree)
    val uncodedText = uncodeText(encodedText, huffmanTree)
    println(encodedText)
    println(uncodedText)
}

fun makeCharsMap(sourceText: String): TreeMap<Char, Int> {
    val charsMap = TreeMap<Char, Int>()
    for (i in sourceText) {
        val t = charsMap[i]
        if (t == null) {
            charsMap[i] = 1
        } else {
            charsMap[i] = t + 1
        }
    }
    return charsMap
}

fun makeHuffmanTree(text: String): HuffmanTree {
    val charsMap = makeCharsMap(text)

    val huffmanTreeList = ArrayList<HuffmanTree>()
    for (i in charsMap) {
        huffmanTreeList.add(HuffmanTree(HuffmanTree.Node().apply {
            char = i.key
            value = i.value
        }))
    }
    while (huffmanTreeList.size > 1) {
        huffmanTreeList.sort()
        huffmanTreeList.add(HuffmanTree(
            HuffmanTree.Node().apply {
                left = huffmanTreeList[0].head
                right = huffmanTreeList[1].head

                value = left!!.value + right!!.value
            }
        ))
        huffmanTreeList.removeAt(0)
        huffmanTreeList.removeAt(0)
    }
    val huffmanTree = huffmanTreeList.first()

    huffmanTree.print()
    println()
    return huffmanTree
}

fun encodeText(text: String, huffmanTree: HuffmanTree): String {
    var encodedText = ""
    val dictionary = huffmanTree.getDictionary()
    for (i in text) {
        encodedText += dictionary[i]
    }
    return encodedText
}
fun uncodeText(text: String, huffmanTree: HuffmanTree): String {
    var encodedText = text
    var uncodedText = ""
    while (encodedText.isNotEmpty()) {
        var node = huffmanTree.head
        while (node.char == '\u0000') {
            node = if (encodedText[0] == '0')
                node.left!!
            else
                node.right!!
            encodedText = encodedText.drop(1)
        }
        uncodedText += node.char
    }
    return uncodedText
}