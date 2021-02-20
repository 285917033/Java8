package com.zgh.lb;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <Description> <br>
 *
 *  stream 不是集合元素，它不是数据结构并不保存数据，它是有关算法和计算的，它更像一个高级版本的 Iterator。
 *  用户在使用普通的 Iterator 时，只能一个一个显式地遍历元素并对其执行某些操作（外部迭代）；
 *  而在使用 Stream 时，用户只需给出对其包含的元素执行什么样的操作即可，比如“过滤出长度大于 10 的字符串”、“获取每个字符串的首字母”等，
 *  流会隐式地在内部进行遍历（内部迭代），并做出相应的数据转换。
 *
 * 与迭代器类似，Stream 是单向的，不可往复，即数据只能遍历一次，遍历过一次后就用尽了，
 * 就像流水从面前流过，一去不复返。与迭代器不同的是，
 * Stream 可以并行化操作，而迭代器只能命令式地、串行化地操作。
 * 当使用串行方式去遍历时，每个 item 读完后再读下一个 item。
 * 而使用并行去遍历时，数据会被分成多个段，其中每一段都在不同的线程中处理，最终将结果合并。
 * Stream 的并行操作依赖于 Java 7 中引入的 Fork/Join 框架（JSR 166y）来拆分任务和加速处理过程。
 *
 * 为什么要使用流
 * Stream 作为 Java 8 的一大亮点，它与 java.io 包里的 InputStream 和 OutputStream 是完全不同的概念。它也不同于 StAX 对 XML 解析的 Stream，也不是 Amazon Kinesis 对大数据实时处理的 Stream。Java 8 中的 Stream 是对集合对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。Stream API 借助于 Lambda 表达式，极大的提高编程效率和程序可读性。同时它提供串行和并行两种模式进行汇聚操作，使用并发模式能够充分利用多核处理器的优势。通常编写并行代码很难而且容易出错, 但使用 Stream API 无需编写一行多线程的代码就可以很方便地写出高性能的并发程序。所以说，Java 8 中首次出现的 Stream 是一个函数式语言 + 多核时代综合影响的产物。
 *
 * 在传统的 J2EE 应用中，Java 代码经常不得不依赖于关系型数据库的聚合操作来完成诸如：客户每月平均消费金额、最贵的在售商品、本周完成的有效订单、取十个数据样本作为首页推荐等等这类的操作，但在当今这个数据大爆炸的时代，数据的来源更加多样化，很多时候不得不脱离 RDBMS，或者以底层返回的数据为基础进行更上层的数据统计。而 Java 的集合 API 中，仅仅有极少量的辅助型方法，很多时候程序员需要用 Iterator 遍历集合并完成相关的聚合应用逻辑。
 *
 *
 * 可以通过集合、值序列、数组、文件或者函数（类似于 Python 中的生成器）等来创建流。
 * 在 Java 8 中，Collection 接口被扩展，增加了两个默认方法来获取 stream
 * @author 朱光和
 * @version 1.0<br>
 * @createDate 2021/02/19 13:52 <br>
 */
public class TestStream {

    public static void main(String[] args) {
        //1.由集合创建流
        List<String> list = new ArrayList<String>();
        Stream<String> stream =  list.stream();


        //2. 由值序列创建流
       Stream<Object> strea2 =  Stream.of("Hello", 123);

       //3.由数组创建的流
        int[] numbers = {1,2,3,4,5};
        IntStream is = Arrays.stream(numbers);
          //java.nio.file.Files 中有很多静态方法都会返回一个流。
        // 比如 Files.lines 方法会返回一个指定文件中的各行构成的字符串流。
        try {
         Stream<String>  lines =   Files.lines(Paths.get("c://a.txt"), Charset.defaultCharset());
         long uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))) // 生成单词流
                 .distinct() // 删除重复项
                 .count();
         System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Stream API 提供了两个静态方法来从函数生成流，包括 Stream.iterate() 和 Stream.generate()，
//        由于这两个操作产生的流都会用给定的函数按需创建值，因此都可以创造出所谓的无限流。

        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);

        Stream.generate(Math::random).limit(5).forEach(System.out::println);

        //筛选出列表中所有的偶数，并确保没有重复
        List<Integer> ls = Arrays.asList(1,3,4,5,5,3,2,34,5,3,10,8,8,10,6,16,18);
       // ls.stream().filter(n -> n % 2 == 0).distinct().forEach(System.out::println);

        //Stream 的切片主要通过 limit 方法和 skip 方法来实现。limit(n) 方法会返回一个不超过给定长度的流，
        // 而 skip(n) 方法会返回一个扔掉了前 n 个元素的流，如果流中元素不足 n 个，则会返回一个空流。
        //

        //映射
        //流的 map 方法接受一个函数（Function<? super T, ? extends R> mapper）作为参数，这个函数会被应用到每个元素上，并将其映射成一个新的元素。
        //
        List<Integer> ls2 = Arrays.asList(1,3,4,5,5,3,2,34,5,3,10,8,8,10,6,16,18);
        ls2.stream().map(n -> n.doubleValue()).collect(Collectors.toList());


        //规约--reduce
        //规约可以将流中所有的元素反复结合最终得到一个值，比如“计算所有苹果的重量”、“所有苹果中最重的是哪个”等。
      // int i =  ls.stream().reduce(1, (a,b) -> a + b);

        //有初始值，利用 Integer 类的静态方法 sum
        int i =  ls.stream().reduce(1, Integer::sum);

        int i1 =  ls.stream().reduce(1, (a,b) -> a * b);

        System.out.println(i1);

        System.out.println(i);

        /// 没有初始值的求和，在没有初始值时，需要考虑流中没有任何元素的情况，因此返回值为 Optional 类型
        Optional<Integer> i2 =  ls.stream().reduce((a,b) -> a + b);
        System.out.println(i2.get());


        int i3 =  ls.stream().reduce(1, Integer::max);

        int i4 =  ls.stream().reduce(1, Integer::min);

        System.out.println(i3);

        System.out.println(i4);

        long i5 =  ls.stream().count();

        System.out.println(i5);

        Optional<Integer> i6 =  ls.stream().map(a->1).reduce(Integer::sum);
        System.out.println(i6);


    }
}
