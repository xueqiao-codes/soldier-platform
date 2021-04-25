package org.soldier.platform.web.mitty;

import java.io.File;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class MittyServer {
	private static CommandLine parse(String[] args) throws ParseException {
		Options opts = new Options();
		
		opts.addOption("d", true, "");
		opts.addOption("f", false, "");
		
		Option opt = opts.getOption("d");
		opt.setValueSeparator('=');
		opt.setArgs(2);
		
		return  new PosixParser().parse(opts, args);
	}
	
	
	public static void main(String[] args) throws Exception{
		CommandLine cl = parse(args);
		if (cl.hasOption("d")) {
			for (Entry<Object, Object> p : cl.getOptionProperties("d").entrySet()) {
				System.setProperty(p.getKey().toString(), p.getValue().toString());
			}
		}
		
		if (Variables.getMittyHome() == null || Variables.getMittyHome().isEmpty()) {
			System.err.println("mitty home is not set");
			return ;
		}
		
		if (!new File(Variables.getMittyHome()).exists()) {
			System.err.println("mitty home is not exist");
			return ;
		}
		
		boolean forceReplaceMittyIni = cl.hasOption("f");
		if(cl.getArgs().length < 1) {
			System.err.println("please set the war");
			return ;
		}
		File warFile = new File(cl.getArgs()[0]);
		if (!warFile.exists() || !warFile.isFile()) {
			System.err.println("war " + warFile.getAbsolutePath() + " is not right");
			return ;
		}
		
		System.out.println("mitty.home=" + Variables.getMittyHome());
		System.out.println("forceReplaceMittyIni=" + forceReplaceMittyIni);
		System.out.println("war=" + warFile.getAbsolutePath());
		
		new WebContainer(warFile, forceReplaceMittyIni).start();
	}
}
