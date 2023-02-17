package collection03;

import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {

		Stack<String> stack = new Stack<>();
		stack.push("A");
		stack.push("B");
		stack.push("C");
		
		String s1 = stack.pop(); // pop : 꺼내기
		System.out.println(s1);
		String s2 = stack.pop();
		System.out.println(s2);
		String s3 = stack.pop();
		System.out.println(s3);
	}

}
