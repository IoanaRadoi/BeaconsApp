package sensors;

import android.bluetooth.BluetoothAdapter;
import android.os.RemoteException;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collections;


public class BeaconManager2 {
    private final BeaconConsumer consumer;
    private final BeaconManager beaconManager;
    private Region region = new Region("region", Collections.EMPTY_LIST);

    public BeaconManager2(BeaconConsumer consumer){
        this.consumer = consumer;
        BluetoothAdapter.getDefaultAdapter().enable();
        beaconManager = BeaconManager.getInstanceForApplication(consumer.getApplicationContext());
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setForegroundBetweenScanPeriod(50);
        beaconManager.setForegroundScanPeriod(300);
        beaconManager.bind(consumer);
    }

    public void startListening(RangeNotifier notifier) {
        beaconManager.setRangeNotifier(notifier);
        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopListening() {
        try {
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unbind() {
        beaconManager.unbind(consumer);
    }

    @Override
    protected void finalize() throws Throwable {
        beaconManager.unbind(consumer);
        super.finalize();
    }
}
