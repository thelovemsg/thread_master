package src;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BankAccountFilter {
    public static void main(String[] args) {

        // 수정된 nums 배열: 유효성 검사를 통과하는 계좌번호 예시
        String[] nums = {"123-456546-7890", "123-456111-7891", "234-567-8901", "345-678-9012", "123456789012"};

        List<String> filteredAccounts = filterAndSortAccounts(nums);
        System.out.println(filteredAccounts);
    }

    private static List<String> filterAndSortAccounts(String[] nums) {
        // 유효한 계좌번호만 필터링
        List<String> validAccounts = Arrays.stream(nums)
                .filter(BankAccountFilter::isValidAccount)
                .collect(Collectors.toList());

        System.out.println("validAccounts = " + validAccounts);

        // 동일 은행 그룹의 계좌 개수에 따라 내림차순 정렬
        Map<String, Long> bankCountMap = validAccounts.stream()
                .map(el -> normalizePattern(el))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // 내림차순 정렬된 결과 출력
        return bankCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> String.valueOf(entry.getValue()))
                .collect(Collectors.toList());
    }

    private static boolean isValidAccount(String account) {
        if (account.startsWith("-") || account.endsWith("-") || account.contains("--")) return false;
        long digitCount = account.chars().filter(Character::isDigit).count();
        long hyphenCount = account.chars().filter(ch -> ch == '-').count();
        // 숫자의 개수와 하이픈의 조건 수정
        return (digitCount >= 11 && digitCount <= 14) && (hyphenCount >= 0 && hyphenCount <= 3) && account.length() <= 20;
    }

    private static String normalizePattern(String account) {
        return account.replaceAll("[0-9]", "0");
    }
}