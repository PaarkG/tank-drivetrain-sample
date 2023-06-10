package frc.team1699.utils;

public abstract class CrashTrackingRunnable implements Runnable {
    public final void run() {
        try {
            runCrashTracked();
        } catch (Throwable t) {
            t.printStackTrace(System.out);
            throw t;
        }
    }

    public abstract void runCrashTracked();
}
