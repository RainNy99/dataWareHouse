package scp;



import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

public class chikatExample {
	static {
		try {
			System.loadLibrary("chilkat");// copy file chilkat.dll vao thu muc project
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public void getTrial() {
		CkGlobal glob = new CkGlobal();
		boolean success = glob.UnlockBundle("Anything for 30-day trial");
		if (success != true) {
			System.out.println(glob.lastErrorText());
			return;
		}
		int status = glob.get_UnlockStatus();
		if (status == 2) {
			System.out.println("Unlocked using purchased unlock code.");
		} else {
			System.out.println("Uncloked in trail mode.");
		}
		System.out.println(glob.lastErrorText());
	}

	public static void main(String argv[]) {
		// This example requires the Chilkat API to have been previously unlocked.
		// See Global Unlock Sample for sample code.

		CkSsh ssh = new CkSsh();
		chikatExample gg = new chikatExample();
		gg.getTrial();
		// Hostname may be an IP address or hostname:
//		String hostname = "www.some-ssh-server.com";
//		String hostname = "http://drive.ecepvn.org:5000/index.cgi?launchApp=SYNO.SDS.App.FileStation3.Instance&launchParam=openfile%3D%252FECEP%252Fsong.nguyen%252FDW_2020%252F&fbclid=IwAR1GjbMt_ZWTairglWCjOQQH6Q0NbyXgl0qP7LTBahWmR4HcJXNVoh5o5fw";
		String hostname = "drive.ecepvn.org";
		int port = 2227;

		// Connect to an SSH server:
		boolean success = ssh.Connect(hostname, port);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return;
		}

		// Wait a max of 5 seconds when reading responses..
		ssh.put_IdleTimeoutMs(5000);

		// Authenticate using login/password:
		success = ssh.AuthenticatePw("guest_access", "123456");
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return;
		}

		// Once the SSH object is connected and authenticated, we use it
		// in our SCP object.
		CkScp scp = new CkScp();

		success = scp.UseSsh(ssh);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}

		scp.put_SyncMustMatch("sinhvien*.*");// down tat ca cac file bat dau bang sinhvien
		String remotePath = "/volume1/ECEP/song.nguyen/DW_2020/data";
		String localPath = "E:\\WH"; // thu muc muon down file ve
		success = scp.SyncTreeDownload(remotePath, localPath, 2, false);

		/*
		 * String remotePath =
		 * "/volume1/ECEP/song.nguyen/DW_2020/data/17130276_Sang_Nhom4.xlsx"; // String
		 * localPath = "/home/bob/test.txt"; String localPath =
		 * "E:\\DATA WAREHOUSE\\Error\\17130276_Sang_Nhom4.xlsx"; success =
		 * scp.DownloadFile(remotePath, localPath);
		 */
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}

		System.out.println("SCP download file success.");

		// Disconnect
		ssh.Disconnect();
	}
}
