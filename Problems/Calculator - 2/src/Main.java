/* Please, do not rename it */
class Problem {


    public static int maxSequence(int[] seq) {

        int max = 0;

        for (int i = 0; i < seq.length; i++) {

            if (seq[i] > max) {
                max = seq[i];
            }

        }
        return max;
    }

    public static int minSequence(int[] seq) {

        int min = seq[0];

        for (int i = 1; i < seq.length; i++) {

            if (seq[i] < min) {
                min = seq[i];
            }

        }
        return min;
    }

    public static int sumSequence(int[] seq) {

        int sum = 0;

        for (int i = 0; i < seq.length; i++) {

            sum += seq[i];

        }
        return sum;
    }

    public static void main(String args[]) {

        String operator = args[0];

        int[] argsRefined = new int[args.length - 1];

        for (int i = 0; i < argsRefined.length; i++) {

            argsRefined[i] = Integer.parseInt(args[i+1]);
           // System.out.println(argsRefined[i]);
        }

        //int[] argsInt = Arrays.asList(args).stream().mapToInt(Integer::parseInt).toArray();

        switch (operator) {

            case "MAX":
                System.out.println(maxSequence(argsRefined));
                break;

            case "MIN":
                System.out.println(minSequence(argsRefined));
                break;

            case "SUM":
                System.out.println(sumSequence(argsRefined));
                break;
        }

    }
}