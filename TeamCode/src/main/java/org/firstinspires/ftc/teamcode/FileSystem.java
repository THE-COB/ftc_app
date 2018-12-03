package org.firstinspires.ftc.teamcode;

import android.content.Context;

import java.io.FileOutputStream;

public abstract class FileSystem {
	public boolean writeAngle(int angle){
		String filename = "myfile";
		String fileContents = "Hello world!";
		FileOutputStream outputStream;

		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(fileContents.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
