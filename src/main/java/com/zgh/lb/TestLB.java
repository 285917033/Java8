package com.zgh.lb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <Description> <br>
 *
 *
 *   在 Java 语言层面的值的形式有两种：原始值和引用值。原始值就是那些基本类型的值，包括 int 类型的值，double 类型的值等。
 *   引用值就是那些引用类型的值，也就是对象的地址值。这些值能够在程序执行期间作为参数进行传递，因此又被称为一等值（或者一等公民）。
 *   与此同时，Java 中的类和方法由于无法作为参数传递而被称为二等公民。但是很多编程语言的实践证明了让方法作为一等值可以使编程变得更加容易，
 *   因此 Java 的设计者们将这个功能加入到了 JDK 8 中，从而使方法可以作为值进行传递。
 *
 *   行为参数化
 *  行为参数化简单来说就是将一个代码块准备好却不马上执行，这部分代码可以作为参数传递给另一个方法，这意味着我们可以推迟这部分代码的执行。
 *  行为参数化是处理频繁的需求变更的一种良好的开发模式
 *
 *
 *  但是实际上 Java 编译器会为匿名类生成一个 ClassName$1 这种形式的类文件。生成大量的类文件是不利的，
 *  因为每个类文件在使用时都需要加载和验证，这会影响应用的启动性能。
 *  在 Java 8 中，我们可以使用 Lambda 表达式来解决这个问题
 *
 *  一，语法
 *
 * Lambda表达式理解为简洁的表示可传递的匿名函数的一种方式，它没有名称，但是它有参数列表，函数主体和返回类型，可能还要异常列表
 * lb表达式有三部分组成：  参数列表，箭头， lb主体 ,基本语法如下：
 *
 * 1. 参数列表, 箭头， 表达式(此处的表达式不带分号)
 *  (parameters) -> expression
 *
 * 2. 参数列表, 箭头， 语句(这里的语句需要花括号包含，语句需要分号)
 *  (parameters) -> { statements; }
 *
 *
 * eg:
 *
 * 1. () -> {}
 *  有效， 没有参数列表， 返回值为void，主体为空
 *
 * 2. () -> "hello world"
 *  有效， 没有参数列表，返回String作为表达式
 *
 * 3. () -> { return "hello world"; }
 *  有效，没有参数列表，使用显示的返回语句返回String
 *
 * 4. (Integer i) -> return "hello world" + i;
 *   无效, return是控制流语句，要使此表达式有效，语句需要花括号包含
 *
 * 5. (String s ) -> {"hello world";}
 *   无效, 因为hello world是一个表达式，不是语句，去掉花括号与分号.
 *
 *
 *  二，使用场景
 *
 *  在函数式接口上使用LB表达式，而函数式接口只定义了一个抽象方法的接口
 *  比如Runnable,他只有一个run()方法，
 *
 *
 *  三，函数描述符
 * Lambda 表达式有参数列表也有返回类型等，这些一起组成了 Lambda 表达式的签名。实际上函数式接口的抽象方法的签名基本上就是 Lambda 表达式的签名，
 * 我们将这个抽象方法叫做函数描述符（Function Descriptor），并且我们使用特殊的表示法来描述 Lambda 表达式和函数描述符的签名。
 * 比如：() -> void 代表了参数列表为空，且返回 void 的函数。下面列举几个可以根据函数描述符判断 Lambda 表达式是否有效的例子。
 *
 * // 有效，因为 Runnable 的签名为 () -> void
 * public void execute(Runnable r) {
 *     r.run();
 * }
 * execute(() -> {});
 *
 * // 有效，因为 fetch() 方法的签名为 () -> String
 * public static Callable<String> fetch() {
 *     return () -> "Hello World";
 * }
 *
 * // 无效，因为 Predicate 接口 test 方法的签名为 (Apple) -> boolean
 * Predicate<Apple> p = (Apple a) -> a.getWeight();
 *
 *
 * @author 朱光和
 * @version 1.0<br>
 * @createDate 2021/02/10 9:44 <br>
 */
public class TestLB {

    public static void main(String[] args) {

        //Supplier<Apple> s = Apple :: new ;

        Supplier<Apple> s = () -> new Apple();
        Apple  a  = s.get();


        Function<String, Apple> s2 = Apple :: new; // 指向 Apple(String color) 的构造函数引用
        Apple  a2  = s2.apply("red");  // 调用该 Function 接口的 apply 方法，并给出要求的color，产生一个新的对象


        // 与上面相等
//        Function<String, Apple> s2 = (String color) -> new Apple(color);
//        Apple  a2  = s2.apply("red");



//        如果构造函数的签名为 Apple(String color, Integer weight)，那么就与 BiFunction 接口的签名 (T, U, R) -> R 一致。
//
//        java.util.function.BiFunction<String, Integer, Apple> f1 = Apple::new;
//        Apple a1 = f1.apply("red", 120);
//
//// 等价于
//        BiFunction<String, Integer, Apple> f2 = (String color, Integer weight) -> new Apple(color, weight);
//        Apple a2 = f2.apply("red", 120);


        Runnable task = () -> System.out.println(Thread.currentThread());

        Thread thread = new Thread(task, "thread0");
        thread.start();

       // forEache(Arrays.asList(1,2,'3'), e -> System.out.println(e));

         forEache(Arrays.asList(1,2,'3'), System.out :: println);


        // List list =  map(Arrays.asList("hello", "world"), (String s) -> s.length());

        List list =  map(Arrays.asList("hello", "world"), String :: length);


        System.out.println(list.size());

        List list2 =  forPredicate(Arrays.asList(4, 5, 6), (i) -> i > 4);

        System.out.println(list2.size());


    }



    /*
     * 1. Predicate<T> 接口定义了一个 test 抽象方法，该方法接受一个泛型 T 对象，返回值boolean，
     *
     * 我们可以理解为该方法的实现是对传入的 T 对象是否具备某些动作状态或者特征。
     *
     */

    static <T> List  forPredicate(List<T> list , Predicate<T> p){
        List<T> result = new ArrayList<T>();

        for(T t: list){
            if(p.test(t)){
                result.add(t);
            }
        }
        return result;
    }



    /*
     * 2. Consumer<T> 接口定义了一个 accept 抽象方法，该方法接受一个泛型 T 对象，没有返回值，
     * 我们可以理解为该方法的实现是对传入的 T 对象进行消费的操作。
     *
     */

    static <T> void forEache(List<T> list , Consumer<T> c){
        for(T t: list){
            c.accept(t);
        }
    }


    /*
     * 3. Function<T, R> 接口定义了一个 apply 抽象方法，该方法接受一个泛型 T 对象，返回泛型R对象，
     * 我们可以理解为该方法的实现是将传入的T对象转换为R对象
     */

    static <T, R> List<R> map(List<T> list, Function<T, R> f){
        List<R> result = new ArrayList<>();
        for(T t: list){
            result.add(f.apply(t));
        }
        return result;
    }

}
