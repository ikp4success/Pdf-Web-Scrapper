import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class pdf_scrape {

	/**
	 * @param args
	 * @throws IOException
	 */

	static Matcher mCase;
	static BufferedWriter writer = null;
	static PrintStream out = null;
	static PrintStream out2 = null;
	static PrintStream listout = null;
	static PrintStream listout2 = null;
	JLabel title_lbl;
	JLabel Startlbl;
	JLabel End_lbl;
	JLabel Year_lbl;
	static JTextField Start_TF = null;
	static JTextField county_TF = null;
	static JTextField End_TF = null;
	static JTextField Year_TF = null;
	
	JLabel hostName_lbl;
	static JTextField hostName_TF = null;
	
	JLabel hostPort_lbl;
	static JTextField hostPort_TF = null;
	
	JLabel userName_lbl;
	static JTextField userName_TF = null;
	
	JLabel psswd_lbl;
	static JTextField psswd_TF = null;
	
	PDDocument document;
	PDDocument document2;
	static String nulltest;
	String[] linkText = null;
	String[] linkText2 = null;
	Boolean StartListScrape = true;
	final FileDialog fc;
	File folder;
	File[] listOfFiles;
	String fileDirectory = "";
	PDFTextStripper stripper;
	PDFTextStripper stripper2;
	JComboBox<String> comboBox;
	Boolean clicked = false;
	Boolean proxyClicked = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new pdf_scrape();
	}

	public pdf_scrape() {
		final JFrame guiFrame = new JFrame();
		guiFrame.setResizable(false);

		// guiFrame.setIconImage(new ImageIcon("C:\\Nql Renamer\\icon.png")
		// .getImage());

		// make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("PA PDF Extractor to HTML");
		guiFrame.setSize(550, 150);

		// This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);

		// The first JPanel contains a JLabel and JCombobox
		final JPanel comboPanel = new JPanel();

		final JRadioButton LScrape = new JRadioButton("List-Scrape");
		LScrape.setMnemonic(KeyEvent.VK_B);
		//LScrape.setActionCommand("List-Scrape");
		LScrape.setSelected(true);
		final JRadioButton useProxy = new JRadioButton("Use Proxy");
		useProxy.setMnemonic(KeyEvent.VK_B);
	
		useProxy.setSelected(true);

		fc = new FileDialog(guiFrame, "Choose a file", FileDialog.LOAD);
		fc.setDirectory("C:\\");

		title_lbl = new JLabel("PA Pdf Scrapper");

		Start_TF = new JTextField(7);
		Start_TF.setText("0000001");

		county_TF = new JTextField(7);
		county_TF.setText("type county here e.g: 25");
		Startlbl = new JLabel("Start Range");

		End_TF = new JTextField(7);
		End_lbl = new JLabel("End Range");

		End_TF = new JTextField(7);
		End_lbl = new JLabel("End Range");

		Year_TF = new JTextField(7);
		Year_lbl = new JLabel("Year");
		
		hostName_TF = new JTextField(7);
		hostName_lbl = new JLabel("HostName");
		hostName_TF.setText("exploder.ion-access.com");

		hostPort_TF = new JTextField(7);
		hostPort_lbl = new JLabel("HostPort");
		hostPort_TF.setText("80");
		
		userName_TF = new JTextField(7);
		userName_lbl = new JLabel("Username");
		userName_TF.setText("asurint");
		psswd_TF = new JTextField(7);
		psswd_lbl = new JLabel("Password");
		psswd_TF.setText("om7aNe3u");

		JLabel Space = new JLabel("                                   ");
		JLabel Space2 = new JLabel("                                   ");
		JButton Execute = new JButton("Execute");
		JButton Result = new JButton("Open Result Folder");
		JButton Browse = new JButton("Browse");
		JButton Clear = new JButton("Clear");

		String[] counties = { "A", "AdamsCounty", "AlleghenyCounty",
				"ArmstrongCounty", "B", "BeaverCounty", "BedfordCounty",
				"BerksCounty", "BlairCounty", "BradfordCounty", "BucksCounty",
				"ButlerCounty", "C", "CambriaCounty", "CameronCounty",
				"CarbonCounty", "CentreCounty", "ChesterCounty",
				"ClarionCounty", "ClearfieldCounty", "ClintonCounty",
				"ColumbiaCounty", "CrawfordCounty", "CumberlandCounty", "D",
				"DauphinCounty", "DelawareCounty", "E", "ElkCounty",
				"ErieCounty", "F", "FayetteCounty", "ForestCounty",
				"FranklinCounty", "FultonCounty", "G", "GreeneCounty", "H",
				"HuntingdonCounty", "I", "IndianaCounty", "J",
				"JeffersonCounty", "JuniataCounty", "L", "LackawannaCounty",
				"LancasterCounty", "LawrenceCounty", "LebanonCounty",
				"LehighCounty", "LuzerneCounty", "LycomingCounty", "M",
				"McKeanCounty", "MercerCounty", "MifflinCounty",
				"MonroeCounty", "MontgomeryCounty", "MontourCounty", "N",
				"NorthamptonCounty", "NorthumberlandCounty", "P",
				"PerryCounty", "PhiladelphiaCounty", "PikeCounty",
				"PotterCounty", "S", "SchuylkillCounty", "SnyderCounty",
				"SomersetCounty", "SullivanCounty", "SusquehannaCounty", "T",
				"TiogaCounty", "U", "UnionCounty", "V", "VenangoCounty", "W",
				"WarrenCounty", "WashingtonCounty", "WayneCounty",
				"WestmorelandCounty", "WyomingCounty", "Y", "YorkCounty" };
		comboBox = new JComboBox<String>(counties);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(title_lbl);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space2);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Startlbl);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Start_TF);

		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(End_lbl);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(End_TF);

		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Year_lbl);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Year_TF);

		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Space);

		comboPanel.add(county_TF);

		comboPanel.add(Space);

		comboPanel.add(Space);
		comboPanel.add(LScrape);
		comboPanel.add(Space);
		comboPanel.add(Clear, BorderLayout.SOUTH);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Execute, BorderLayout.SOUTH);

		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Result, BorderLayout.SOUTH);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Browse, BorderLayout.SOUTH);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(comboBox);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Space2);
		comboPanel.add(Space2);
		comboPanel.add(Space);
		comboPanel.add(Space);

		comboPanel.add(Space2);
		comboPanel.add(Space2);

		comboPanel.add(hostName_lbl,BorderLayout.SOUTH);
		comboPanel.add(hostName_TF,BorderLayout.SOUTH);
		comboPanel.add(Space);
		comboPanel.add(Space2);

		comboPanel.add(Space2);
		comboPanel.add(hostPort_lbl);
		comboPanel.add(hostPort_TF);
		comboPanel.add(Space);
		comboPanel.add(Space2);

		comboPanel.add(Space2);
		comboPanel.add(userName_lbl);
		comboPanel.add(userName_TF);
		comboPanel.add(Space);
		

		comboPanel.add(Space2);
		comboPanel.add(psswd_lbl);
		comboPanel.add(psswd_TF);
		comboPanel.add(Space);
		comboPanel.add(useProxy);
		comboPanel.add(Space);
		// comboPanel.add(fc);
		guiFrame.add(comboPanel, BorderLayout.CENTER);

		guiFrame.setVisible(true);

		Result.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				String path = "C:\\pdfScrapper\\" + comboBox.getSelectedItem()
						+ "\\"; // path to the directory
				// to be
				// opened
				File folder = new File(path);

				Desktop desktop = null;
				// Before more Desktop API is used, first check
				// whether the API is supported by this particular
				// virtual machine (VM) on this particular host.
				if (Desktop.isDesktopSupported()) {
					desktop = Desktop.getDesktop();
				}
				try {
					if (folder.exists()) {
						desktop.open(folder);
					}
				} catch (IOException e) {
				}

			}
		});

		Clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				LScrape.setSelected(false);
				StartListScrape = false;
				useProxy.setSelected(false);
				guiFrame.repaint();
			}
		});

		Browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				fc.setVisible(true);

				folder = new File(fc.getDirectory());
				listOfFiles = folder.listFiles();

				for (File file : listOfFiles) {
					if (file.isFile()) {

						fileDirectory = fc.getDirectory() + fc.getFile();

					}
				}
			}
		});

	
		
		LScrape.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			    	StartListScrape = true;
			    }
			    else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	StartListScrape = false;
			    }
			}

			
		});
		
		useProxy.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			    	proxyClicked=true;
			    }
			    else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	proxyClicked=false;
			    }
			}

			
		});

		Execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					

					String destDir = "C:\\pdfScrapper\\"
							+ comboBox.getSelectedItem() + "\\";
					mkDir(destDir);
					if (StartListScrape = true) {
						String url2 = "";
						String rangeren2 = "";
						String year = Year_TF.getText().trim();
						String county = county_TF.getText().trim();
						BufferedReader br = null;

						List<Integer> listScrp = new ArrayList<Integer>();
						List<Integer> listScrp2 = new ArrayList<Integer>();
						List<String> listScrpadd = new ArrayList<String>();
						String sCurrentLine;
						String[] scrLi = new String[200000];
						br = new BufferedReader(new FileReader(fileDirectory));
						System.out.println("Filedir: " + fileDirectory);

						while ((sCurrentLine = br.readLine()) != null) {

							// scrLi = sCurrentLine.split(",");
							listScrpadd.add(CaseNumbersplitter(sCurrentLine));
						}

						for (int i = 0; i <= listScrpadd.size() - 1; i++) {
							try {
								// int range2 = Integer.parseInt(scrLi[i]);
								if (i != (listScrpadd.size() + 1)) {
									File file = new File(destDir + "case_"
											+ year + "-" + listScrpadd.get(i)
											+ "-" + county + ".htm");

									File file2 = new File(destDir + "log.yaml");
									listout = new PrintStream(
											new FileOutputStream(file));
									listout2 = new PrintStream(
											new FileOutputStream(file2));
									listout2.println("INPUT DATA");
									listout2.println("CaseYear: " + year);
									listout2.println("County: " + county);
									listout2.println("County Selected: "
											+ comboBox.getSelectedItem());

									listout2.println(comboBox.getSelectedItem()
											+ " Job Started at:"
											+ DateFormat
													.getDateTimeInstance()
													.format(new Date(
															System.currentTimeMillis())));

									System.out.println("liststuff: "
											+ listScrpadd.get(i));

									// System.out.println("rngestuff: "+
									// range2);

									listScrp.add(Integer.valueOf(listScrpadd
											.get(i)));
									// listScrp2.add(Integer.valueOf(listScrpadd.get(i)));

									if (listScrp.get(i) > 0
											&& listScrp.get(i) < 10) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ "000000"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();
										// rangeren2= "000000"+i;
									} else if (listScrp.get(i) >= 10
											&& listScrp.get(i) < 100) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ "00000"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();

										// rangeren2= "00000"+i;
									} else if (listScrp.get(i) >= 100
											&& listScrp.get(i) < 1000) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ "0000"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();

										// rangeren2= "00000"+i;
									} else if (listScrp.get(i) >= 1000
											&& listScrp.get(i) < 10000) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ "000"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();

										// rangeren2= "00000"+i;
									} else if (listScrp.get(i) >= 10000
											&& listScrp.get(i) < 100000) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ "00"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();

										// rangeren2= "00000"+i;
									} else if (listScrp.get(i) >= 100000
											&& listScrp.get(i) < 1000000) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ "0"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();

										// rangeren2= "00000"+i;
									} else if (listScrp.get(i) >= 1000000
											&& listScrp.get(i) < 10000000) {
										url2 = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-"
												+ county
												+ "-CR-"
												+ listScrp.get(i)
												+ "-"
												+ Year_TF.getText().trim();

										// rangeren2= "00000"+i;
									}

									System.out.println("liststuff3: "
											+ listScrp.get(i));
									// out2.println(url);
									if(proxyClicked==true){
									
										
									Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName_TF.getText().toString(), Integer.parseInt(hostPort_TF.getText().toString())));
									Authenticator authenticator = new Authenticator() {

								        public PasswordAuthentication getPasswordAuthentication() {
								            return (new PasswordAuthentication(userName_TF.getText().toString(),psswd_TF.getText().toString().toCharArray()));
								        }
								    };
								    if(!userName_TF.getText().toString().isEmpty() || !psswd_TF.getText().toString().isEmpty()){
								    	  Authenticator.setDefault(authenticator);
								    }
								    try{
									URLConnection conn = new URL(url2).openConnection(proxy);
									conn.connect();
									
									BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
									String response = reader.readLine();
									
									if(response!=null){
									document2 = PDDocument.load(conn.getURL());
									}else{
										
										document2.close();
									}
									}catch(UnknownHostException uex){
										listout2.println("\r\nCONNECTION ERROR\r\n");
										listout2.println(uex);
										listout2.println("\r\n Connection Failed at "
												+ DateFormat.getDateTimeInstance()
														.format(new Date(System
																.currentTimeMillis()))
												
												+ "\r\n"
												+ "Reason: Proxy Failed or WebUrl Invalid \r\n"
													);
										
										
									
										

										
									}catch(ConnectException cex){
										listout2.println("\r\nCONNECTION ERROR\r\n");
										listout2.println(cex);
										listout2.println("\r\n Connection Failed at "
												+ DateFormat.getDateTimeInstance()
														.format(new Date(System
																.currentTimeMillis()))
												
												+ "\r\n"
												+ "Reason: Proxy Failed or WebUrl Invalid \r\n"
													);
										
										
									
										

										
									}
									}else if(proxyClicked==false){
										document2 = PDDocument.load(new URL(url2));
									}

								} else {
									System.out.println(comboBox
											.getSelectedItem()
											+ " finished at:"
											+ DateFormat
													.getDateTimeInstance()
													.format(new Date(
															System.currentTimeMillis()))
											+ "\r\n");

								}

							} catch (IOException e) {
							
								System.out.println(e);
								// e.printStackTrace();
								
								listout2.println("\r\nERROR DETAILS\r\n");
								listout2.println(e);
								listout2.println("\r\nCaseNumber Link Failed at "
										+ DateFormat.getDateTimeInstance()
												.format(new Date(System
														.currentTimeMillis()))
										
										+ "\r\n"
										+ "Reason: Either Case Number is wrong or does not exist");
								
								//listout2.close();
								

							}

							if (document2 != null) {

								stripper2 = new PDFTextStripper();
								linkText2 = new String[200000];
								String cse_id = null;
								Pattern r = Pattern.compile("=(.*?)&");

								// linkText =
								// stripper.getText(document).split("OTN:");

								// int count = document.getNumberOfPages();
								// String count2 = count+"";
								// stripper.setPageEnd(count2.trim());
								// stripper2.getPageEnd();
								stripper2.setSortByPosition(true);

								// stripper.getAddMoreFormatting();

								// Matcher m = r.matcher(linkText);
								//
								//
								try {
									List<String> allList2 = new ArrayList<String>();

									String dta = stripper2.getText(document2);
									if (!dta.isEmpty() || dta != null)
										allList2.add(dta);
									// stripper.
									// Iterator<String> iterator1 =
									// allList.iterator();
									//
									// while(iterator1.hasNext()){
									//
									// allList.add(iterator1.next());
									//
									// }

									listout.println("<html>");
									listout.println("<body>");
									listout.println("<table>");
									listout.println("<tbody>");
									int x = 0;

									for (String list : allList2) { // use a for
																	// each loop
																	// instead
																	// of
																	// looping
																	// on an int
																	// and using
																	// allList.get(i)

										// String list2 = list + "\n";
										String[] str = list.split("\n"); // You
																			// need
																			// to
																			// escape
																			// |
																			// with
																			// \\
																			// to
																			// get
																			// the
																			// expected
																			// split

										// String item = allList.add(str[i2]);
										// for (String item : str) {
										for (int i2 = 0; str.length - 1 > i2; i2++) {
											x = i2;
											// allList.add(str[i2]);
											// for(int i3 = 2;
											// allList.indexOf(0)>i3; i3++){

											// if (!str[i2].contains("§")) {
											// System.out.println("<tbody><tr><td>"
											// + str[i2].trim()
											// + "</td></tr></tbody>");
											//
											// listout.println("<tbody><tr><td>"
											// + str[i2].trim()
											// + "</td></tr></tbody>");
											// // }
											// // }
											// // if (item.contains(",")) {
											// // System.out.println("hii");
											// // }
											// }

											if (str[i2]
													.contains("CASE INFORMATION")) {
												if (x < str.length - 1) {

													x++;
													System.out
															.println("<tbody id=CASEINFORMATION>");
													listout.println("<tbody id=CASEINFORMATION>");
													while (!str[x]
															.contains("CASE INFORMATION")

													) {
														// System.out.println(str[x]);
														listout.println("<tr><td>"
																+ str[x].trim()
																+ "</td></tr>");
														System.out.println("<tr><td><tr>"
																+ str[x].trim()
																+ "</tr><tr><td>");
														x = x + 1;
														if (x < str.length - 1) {
															if (str[x]
																	.contains("STATUS INFORMATION")) {
																break;
															}
														} else {
															break;
														}
													}
												}
												System.out.println("</tbody>");
												listout.println("</tbody>");

											}
											if (str[i2]
													.contains("STATUS INFORMATION")) {
												if (x < str.length - 1) {

													x++;
													System.out
															.println("<tbody id=STATUSINFORMATION>");
													listout.println("<tbody id=STATUSINFORMATION>");
													while (!str[x]
															.contains("STATUS INFORMATION")

													) {
														// System.out.println(str[x]);
														listout.println("<tr><td>"
																+ str[x].trim()
																+ "</td></tr>");
														System.out.println("<tr><td><tr>"
																+ str[x].trim()
																+ "</tr><tr><td>");
														x = x + 1;
														if (x < str.length - 1) {
															if (str[x]
																	.contains("DEFENDANT INFORMATION")) {
																break;
															}
														} else {
															break;
														}
													}
												}
												System.out.println("</tbody>");
												listout.println("</tbody>");

											}

											if (str[i2]
													.contains("DEFENDANT INFORMATION")) {
												if (x < str.length - 1) {

													x++;
													System.out
															.println("<tbody id=DEFENDANTINFORMATION>");
													listout.println("<tbody id=DEFENDANTINFORMATION>");
													while (!str[x]
															.contains("DEFENDANT INFORMATION")

													) {
														// System.out.println(str[x]);
														listout.println("<tr><td>"
																+ str[x].trim()
																+ "</td></tr>");
														System.out.println("<tr><td><tr>"
																+ str[x].trim()
																+ "</tr><tr><td>");
														x = x + 1;
														if (x < str.length - 1) {
															if (str[x]
																	.contains("CASE PARTICIPANTS")) {
																break;
															}
														} else {

														}
													}
												}
												System.out.println("</tbody>");
												listout.println("</tbody>");

											}
											if (str[i2]
													.contains("CASE PARTICIPANTS")) {
												if (x < str.length - 1) {

													x++;
													System.out
															.println("<tbody id=CASEPARTICIPANTS>");
													listout.println("<tbody id=CASEPARTICIPANTS>");
													while (!str[x]
															.contains("CASE PARTICIPANTS")

													) {
														// System.out.println(str[x]);
														listout.println("<tr><td>"
																+ str[x].trim()
																+ "</td></tr>");
														System.out.println("<tr><td><tr>"
																+ str[x].trim()
																+ "</tr><tr><td>");
														x = x + 1;
														if (x < str.length - 1) {
															if (str[x]
																	.contains("CHARGES")) {
																break;
															}
														} else {
															break;
														}
													}
												}
												System.out.println("</tbody>");
												listout.println("</tbody>");

											} else if (str[i2]
													.contains("CHARGES")) {

												x++;
												System.out
														.println("<tbody id=Charges>");
												listout.println("<tbody id=Charges>");
												while (!str[x]
														.contains("CHARGES")

												) {
													// System.out.println(str[x]);
													listout.println("<tr><td>"
															+ str[x].trim()
															+ "</td></tr>");
													System.out.println("<tr><td><tr>"
															+ str[x].trim()
															+ "</tr><tr><td>");

													x = x + 1;
													if (x < str.length - 1) {
														if (str[x]
																.contains("DISPOSITION SENTENCING/PENALTIES")) {
															break;
														}
													} else {
														break;
													}

												}

												System.out.println("</tbody>");
												listout.println("</tbody>");

											}
											// if (str[i2]
											// .contains("Proceed to Court")) {
											// x++;
											// System.out
											// .println("<tbody id=DISPOSITIONSENTENCING>");
											// listout.println("<tbody id=DISPOSITIONSENTENCING>");
											// while (!str[x]
											// .contains("DISPOSITIONSENTENCING")
											//
											// ) {
											// // System.out.println(str[x]);
											// listout.println("<tr><td>"
											// + str[x].trim()
											// + "</td></tr>");
											// System.out.println("<tr><td><tr>"
											// + str[x].trim()
											// + "</tr><tr><td>");
											// x = x + 1;
											// if (str[x]
											// .contains("COMMONWEALTH INFORMATION"))
											// {
											// break;
											// }
											// }
											// System.out.println("</tbody>");
											// listout.println("</tbody>");
											//
											// }
											if (str[i2].contains("ENTRIES")) {
												if (x < str.length - 1) {

													x++;
													System.out
															.println("<tbody id=ENTRIES>");
													listout.println("<tbody id=ENTRIES>");
													while (!str[x]
															.contains("ENTRIES")

													) {
														// System.out.println(str[x]);
														listout.println("<tr><td>"
																+ str[x].trim()
																+ "</td></tr>");
														System.out.println("<tr><td><tr>"
																+ str[x].trim()
																+ "</tr><tr><td>");
														x = x + 1;

														if (str[x]
																.contains("CPCMS")) {
															break;
														}
													}
												}
												System.out.println("</tbody>");
												listout.println("</tbody>");

											} else {
												x = 0;
												// continue;
											}

										}
										// }

									}

									//
									// for(int i2 = 0; i2<linkText.length;
									// i2++){
									// System.out.println("<tr>"+linkText[i2]+"</tr>");
									//
									// out.println("<tr><td>"+linkText[i2]+"</td></tr>");
									//
									// // mCase= r.matcher(linkText[2]);
									//
									// // if (mCase.find()) {
									// // // m.toString();/
									// //System.out.println(linkText[4]);
									// //out.println(linkText[i]);
									// // // s.add(m.group(1));
									// // cse_id = mCase.group(1);
									// // }
									//
									// }
									listout.println("</tbody>");
									listout.println("</table>");

									listout.println("</body>");
									listout.println("</html>");

								} catch (IndexOutOfBoundsException ix) {
									ix.printStackTrace();
									System.out
											.println("Case Number wrong or case doesn't exist: Error:  "
													+ ix);

								}

								document2.close();
								listout.close();
								//listout2.close();
								br.close();

							}
						}
						listout2.println("\r\n"+comboBox.getSelectedItem()
								+ " finished at:"
								+ DateFormat.getDateTimeInstance().format(
										new Date(System.currentTimeMillis()))
								+ "\r");

						document2.close();
						listout.close();
						listout2.close();
						br.close();
						// listScrp.clear();
						// br.close();

					}

					else if (StartListScrape == false) {
						String start_range = "";
						String end_range = "";

						start_range = Start_TF.getText().trim();
						end_range = End_TF.getText().trim();
						String year = Year_TF.getText().trim();

						List<Integer> myList = new ArrayList<Integer>();

						// number_split =
						int start_range2 = Integer.parseInt(start_range);
						int end_range2 = Integer.parseInt(end_range);
						String rangeren = "";

						// System.out
						// .println("CaseNumber|FullName|Aliase|DOB|Gender|Race|HairColor|EyeColor");
						// out.println("CaseNumber|FullName|Aliase|DOB|Gender|Race|HairColor|EyeColor");

						String url = "";

						for (int i = start_range2; i <= end_range2; i++) {

							try {
								// myList.add(i);

								if (i < 10) {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"
											+ "000000"
											+ i
											+ "-"
											+ Year_TF.getText().trim();
									rangeren = "000000" + i;
								} else if (i >= 10 && i < 100) {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"
											+ "00000"
											+ i
											+ "-"
											+ Year_TF.getText().trim();

									rangeren = "00000" + i;
								} else if (i >= 100 && i < 1000) {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"
											+ "0000"
											+ i
											+ "-"
											+ Year_TF.getText().trim();

									rangeren = "0000" + i;
								} else if (i >= 1000 && i < 10000) {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"
											+ "000"
											+ i
											+ "-"
											+ Year_TF.getText().trim();

									rangeren = "000" + i;
								} else if (i >= 10000 && i < 100000) {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"
											+ "00"
											+ i
											+ "-"
											+ Year_TF.getText().trim();

									rangeren = "00" + i;
								} else if (i >= 100000 && i < 1000000) {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"
											+ "0"
											+ i
											+ "-"
											+ Year_TF.getText().trim();

									rangeren = "0" + i;
								} else {
									url = "https://ujsportal.pacourts.us/DocketSheets/CPReport.ashx?docketNumber=CP-25-CR-"

											+ i
											+ "-"
											+ Year_TF.getText().trim();

									rangeren = "" + i;
								}

								// out2.println(url);
								document = PDDocument.load(new URL(url));

								if (rangeren != null) {
									File file = new File(
											"M:\\ScrapeAdmin\\PAScrape\\case_"
													+ year + "-" + rangeren
													+ ".htm");
									File file2 = new File(
											"M:\\ScrapeAdmin\\PAScrape\\yaml.txt");
									out = new PrintStream(new FileOutputStream(
											file));
									out2 = new PrintStream(
											new FileOutputStream(file2));
								}

							} catch (IOException e) {
								System.out.println(e);
								out2.println("CaseNumber Failed: " + e);

							} catch (IndexOutOfBoundsException ix) {
								System.out
										.println("Case Number worng or doesn't exist: Error: "
												+ ix);
								out2.println("Case Number wrong or case doesn't exist: Error: "
										+ ix + "\r");
							}
							if (document != null) {

								stripper = new PDFTextStripper();
								linkText = new String[200000];
								String cse_id = null;
								Pattern r = Pattern.compile("=(.*?)&");

								// linkText =
								// stripper.getText(document).split("OTN:");

								int count = document.getNumberOfPages();
								String count2 = count + "";
								// stripper.setPageEnd(count2.trim());
								stripper.getPageEnd();
								stripper.setSortByPosition(true);
								// stripper.getAddMoreFormatting();

								// Matcher m = r.matcher(linkText);
								//
								//
								try {
									List<String> allList = new ArrayList<String>();
									List<String> allList2 = new ArrayList<String>();

									allList.add((stripper.getText(document)
											.trim()));
									// stripper.
									// Iterator<String> iterator1 =
									// allList.iterator();
									//
									// while(iterator1.hasNext()){
									//
									// allList.add(iterator1.next());
									//
									// }

									out.println("<html>");
									out.println("<body>");
									out.println("<table>");
									out.println("<tbody>");

									for (String list : allList) { // use a for
																	// each loop
																	// instead
																	// of
																	// looping
																	// on an int
																	// and using
																	// allList.get(i)
										// String list2 = list + "\n";
										String[] str = list.split("\n"); // You
																			// need
																			// to
																			// escape
																			// |
																			// with
																			// \\
																			// to
																			// get
																			// the
																			// expected
																			// split

										// String item = allList.add(str[i2]);
										// for (String item : str) {
										for (int i2 = 2; str.length > i2; i2++) {
											// allList.add(str[i2]);
											// for(int i3 = 2;
											// allList.indexOf(0)>i3; i3++){

											if (!str[i2].contains("§")) {
												System.out.println("<tr>"
														+ str[i2].trim()
														+ "</tr>");

												out.println("<tbody><tr><td>"
														+ str[i2].trim()
														+ "</td></tr></tbody>");
												// }
												// }
												// if (item.contains(",")) {
												// System.out.println("hii");
												// }
											}
											if (str[i2].contains("§")) {
												out.println("<tr><td>"
														+ str[i2].trim()
														+ "</td></tr>");
												System.out.println("<tr>"
														+ str[i2].trim()
														+ "</tr>");

											} else {
												// continue;
											}
										}
										// }

									}

									//
									// for(int i2 = 0; i2<linkText.length;
									// i2++){
									// System.out.println("<tr>"+linkText[i2]+"</tr>");
									//
									// out.println("<tr><td>"+linkText[i2]+"</td></tr>");
									//
									// // mCase= r.matcher(linkText[2]);
									//
									// // if (mCase.find()) {
									// // // m.toString();/
									// //System.out.println(linkText[4]);
									// //out.println(linkText[i]);
									// // // s.add(m.group(1));
									// // cse_id = mCase.group(1);
									// // }
									//
									// }
									out.println("</tbody>");
									out.println("</table>");

									out.println("</body>");
									out.println("</html>");

									// if(nulltest!=("false")){

									// System.out.println(CaseNumbersplitter(linkText[13]
									// .trim())
									// + "|"
									// + linkText[2].trim()
									// + "|"
									// + linkText[5].trim()
									// + "|"
									// + splitter(linkText[6].trim())
									// + "|"
									// + splitter(linkText[10].trim())
									// + "|"
									// + splitter(linkText[7].trim())
									// + "|"
									// + splitter(linkText[8].trim())
									// + "|"
									// + splitter(linkText[9].trim()));
									//
									// String casedata =
									// "CaseNumber|FullName|Aliase|DOB|Gender|Race|HairColor|EyeColor"
									// + "\r"
									// + CaseNumbersplitter(linkText[13].trim())
									// + "|"
									// + linkText[2].trim()
									// + "|"
									// + linkText[5].trim()
									// + "|"
									// + splitter(linkText[6].trim())
									// + "|"
									// + splitter(linkText[10].trim())
									// + "|"
									// + splitter(linkText[7].trim())
									// + "|"
									// + splitter(linkText[8].trim())
									// + "|"
									// + splitter(linkText[9].trim());
									//
									// out.println(CaseNumbersplitter(linkText[13].trim())
									// + "|" + linkText[2].trim() + "|"
									// + linkText[5].trim() + "|"
									// + splitter(linkText[6].trim()) + "|"
									// + splitter(linkText[10].trim()) + "|"
									// + splitter(linkText[7].trim()) + "|"
									// + splitter(linkText[8].trim()) + "|"
									// + splitter(linkText[9].trim()));

									// System.out.println("still Loading" + i);
									// }
								} catch (IndexOutOfBoundsException ix) {
									System.out
											.println("Case Number worng or doesnt exist: Error: "
													+ ix);
									listout2.println("Case Number worng or doesnt exist: Error: "
											+ ix + "\r");
								}

								document.close();

								//
							}
							out.close();
							out2.close();

							// System.out.println(m.group(1));
							// myList.clear();
						}
					}

				} catch (IOException e) {
					System.out.println("Case Failed: " + e);

				}

				// for(int i2 = 11; i2<linkText.length; i2++){
				//
				// // mCase= r.matcher(linkText[2]);
				//
				// // if (mCase.find()) {
				// // // m.toString();/
				// System.out.println(linkText[i2]);
				// out.println(linkText[i2]);

				// }

			}

		});

	}

	public static String splitter(String Split_Reciever) {
		String[] split_Array = new String[2];
		String[] split_Array2 = new String[2];
		split_Array = Split_Reciever.split(":");

		for (int j = 0; j < split_Array.length; j++) {

			split_Array2[j] = split_Array[j];

		}

		return split_Array2[1];

	}

	public static String CaseNumbersplitter(String CaseSplit_Reciever) {

		String[] split_Array = CaseSplit_Reciever.split(",");

		return split_Array[0];

	}

	public static String ZeroNumbersplitter(String CaseSplit_Reciever) {
		String[] split_Array = new String[200000];
		split_Array = CaseSplit_Reciever.split(",");

		return split_Array[0];

	}

	public static void mkDir(String htmldestinationPath) {

		File fileDir = new File(htmldestinationPath);

		if (!fileDir.exists()) {
			fileDir.mkdir();

		}

	}

}
