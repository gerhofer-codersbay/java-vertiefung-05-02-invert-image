package codersbay;

import java.util.concurrent.RecursiveAction;

public class ForkInvert extends RecursiveAction {

    // TODO change this and document timings
    static int THRESHOLD = 1_000;

    private int[] sourceImage;
    private int start;
    private int end;
    private int[] destinationImage;

    public ForkInvert(int[] sourceImage, int start, int end, int[] destinationImage) {
        this.sourceImage = sourceImage;
        this.start = start;
        this.end = end;
        this.destinationImage = destinationImage;
    }

    @Override
    protected void compute() {
        // TODO split if below threshhold invert pixels in range otherwise
    }
}
