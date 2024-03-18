package javadatastructure.listimplment.stack;

public class ReverseString {
    public static void main(String[] args) {
        String input = "Test seq 12345";
        String t = reverse(input);
        System.out.println("Input string: " + input);
        System.out.println("Reversed string: " + t);
    }
    
    private static String reverse(String s) {
        ArrayStack<Character> st = new ArrayStack<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            st.push(s.charAt(i));
        }
        String output = "";
        while(!st.isEmpty()) {
            output = output + st.pop();
        }
        return output;
    }
}
