package secondweek.sequencegenerator;

public class SequenceGeneratorImpl implements SequenceGenerator {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;

    @Override
    public void a(int n) {
        for (int i = TWO; i <= TWO * n; i++) {
            if (i % TWO == 0) {
                System.out.println(i);
            }
        }
    }

    @Override
    public void b(int n) {
        for (int i = ONE; i <= TWO * n; i++) {
            if (i % TWO != 0) {
                System.out.println(i);
            }
        }
    }

    @Override
    public void c(int n) {
        for (int i = ONE; i <= n; i++) {
            System.out.println(i * i);
        }
    }

    @Override
    public void d(int n) {
        for (int i = ONE; i <= n; i++) {
            System.out.println(i * i * i);
        }
    }

    @Override
    public void e(int n) {
        for (int i = ONE; i <= n; i++) {
            System.out.println(i % TWO != 0 ? +ONE : -ONE);
        }
    }

    @Override
    public void f(int n) {
        for (int i = ONE; i <= n; i++) {
            System.out.println(i % TWO != 0 ? +i : -i);
        }
    }

    @Override
    public void g(int n) {
        for (int i = ONE; i <= n; i++) {
            System.out.println(i % TWO != 0 ? +(i * i) : -(i * i));
        }
    }

    @Override
    public void h(int n) {
        int j = ONE;
        for (int i = ONE; i <= n; i++) {
            if (i % TWO != 0) {
                System.out.println(j);
                j++;
            } else {
                System.out.println(ZERO);
            }
        }
    }

    @Override
    public void i(int n) {
        long fact = ONE;
        for (int i = ONE; i <= n; i++) {
            fact = fact * i;
            System.out.println(fact);
        }
    }

    @Override
    public void j(int n) {
        int previous = ZERO;
        int next = ONE;
        int sum;

        for (int i = 1; i <= n; i++) {
            System.out.println(next);
            sum = previous + next;
            previous = next;
            next = sum;
        }
    }
}
