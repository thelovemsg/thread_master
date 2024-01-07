package section03;

import java.math.BigInteger;

public class Blocking2 {
    public static void main(String[] args) throws InterruptedException {
        //너무 큰 수룰 계산하는 경우 thread를 멈춰야 하는데 이 경우에는 Thread.interrupt도 부족하다.
        Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("2")));
        thread.setDaemon(true);
        thread.start();
        thread.sleep(1000);
        //이거롤도 안되면 코드 내에서 시간이 오래 걸리는 핫스팟을 찾아야 한다.
        thread.interrupt();


    }

    public static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                // interrupt되었는지 각각의 반목분에서 확인
//                if(Thread.currentThread().isInterrupted()) {
//                    System.out.println("Prematurely interrupted computation");
//                    return BigInteger.ZERO;
//                }

                //만약 정상적으로 멈출 필요가 없다면 daemon thread 설정을 해주면 된다.
                result = result.multiply(base);
            }

            return result;
        }
    }
}
