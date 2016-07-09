package com.accenture.pi;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.usb.UsbConfiguration;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbEndpoint;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbInterface;
import javax.usb.UsbPipe;
import javax.usb.UsbServices;
import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;

import com.accenture.pi.timer.Status;
import static com.accenture.pi.timer.Status.EMPTY;
import static com.accenture.pi.timer.Status.OVERWEIGHT;
import static com.accenture.pi.timer.Status.NEGATIVE;

/**
 * Self-contained implementation that connects to a USB scale and outputs the
 * data returned to the command line.
 *
 */
public class UsbScaleTest implements UsbPipeListener {

	private final UsbDevice device;
	private UsbInterface iface;
	private UsbPipe pipe;
	private byte[] data = new byte[6];
	private Status status = new Status();

	private UsbScaleTest(UsbDevice device) {
		this.device = device;
	}

	public static void main(String[] args) throws UsbException, InterruptedException {
		UsbScaleTest scale = UsbScaleTest.findScale();
		scale.open();
		try {

			// for (int i = 0; i < 60; i++) { scale.syncSubmit();}
			while (scale.iface.isActive()) {
				scale.syncSubmit();
			}
		} finally {
			scale.close();
		}
	}

	public static UsbScaleTest findScale() throws UsbException {
		UsbServices services = UsbHostManager.getUsbServices();
		UsbHub rootHub = services.getRootUsbHub();
		// Dymo M10 Scale:
		UsbDevice device = findDevice(rootHub, (short) 0x0922, (short) 0x8003);
		// Dymo M25 Scale:
		if (device == null) {
			device = findDevice(rootHub, (short) 0x0922, (short) 0x8004);
		}
		if (device == null) {
			return null;
		}
		return new UsbScaleTest(device);
	}

	private static UsbDevice findDevice(UsbHub hub, short vendorId, short productId) {
		for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()) {
			UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
			if (desc.idVendor() == vendorId && desc.idProduct() == productId) {
				return device;
			}
			if (device.isUsbHub()) {
				device = findDevice((UsbHub) device, vendorId, productId);
				if (device != null) {
					return device;
				}
			}
		}
		return null;
	}

	private void open() throws UsbException {
		UsbConfiguration configuration = device.getActiveUsbConfiguration();
		iface = configuration.getUsbInterface((byte) 0);
		// this allows us to steal the lock from the kernel
		iface.claim(usbInterface -> true);
		final List<UsbEndpoint> endpoints = iface.getUsbEndpoints();
		pipe = endpoints.get(0).getUsbPipe(); // there is only 1 endpoint
		pipe.addUsbPipeListener(this);
		pipe.open();
	}

	private void syncSubmit() throws UsbException {
		pipe.syncSubmit(data);
	}

	public void close() throws UsbException {
		pipe.close();
		iface.release();
	}

	@Override
	public void dataEventOccurred(UsbPipeDataEvent upde) {
		boolean empty = data[1] == 2;
		boolean overweight = data[1] == 6;
		boolean negative = data[1] == 5;
		boolean grams = data[2] == 2;
		int scalingFactor = data[3];
		int weight = (data[4] & 0xFF) + (data[5] << 8);
		double wt = 0.0;
		
		String updatedStatus;
		
		if (empty) {
			updatedStatus = EMPTY;
		} else if (overweight) {
			updatedStatus = OVERWEIGHT;
		} else if (negative) {
			updatedStatus = NEGATIVE;
		} else {
			wt = scaleWeight(weight, scalingFactor);
			updatedStatus = String.format("Weight = %,.1f%s", wt, grams ? " g" : " oz");
		}
		
		if(updatedStatus.equals(status.getStatus())) {
			return;
		} 
		
		System.out.println(updatedStatus);
		
		/*status.setWeight(wt);
		status.setStatus(updatedStatus);
		System.out.println(updatedStatus);*/
	}

	private double scaleWeight(int weight, int scalingFactor) {
		return weight * Math.pow(10, scalingFactor);
	}

	@Override
	public void errorEventOccurred(UsbPipeErrorEvent upee) {
		Logger.getLogger(UsbScaleTest.class.getName()).log(Level.SEVERE, "Scale Error", upee);
	}
	
}
