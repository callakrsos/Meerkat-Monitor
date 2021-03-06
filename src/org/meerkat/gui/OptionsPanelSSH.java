/**
 * Meerkat Monitor - Network Monitor Tool
 * Copyright (C) 2011 Merkat-Monitor
 * mailto: contact AT meerkat-monitor DOT org
 * 
 * Meerkat Monitor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Meerkat Monitor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with Meerkat Monitor.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.meerkat.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.meerkat.services.SecureShellSSH;
import org.meerkat.util.MasterKeyManager;
import org.meerkat.webapp.WebAppCollection;
import org.meerkat.webapp.WebAppResponse;

public class OptionsPanelSSH extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1074428943057064795L;
	private SecureShellSSH webApp;
	private WebAppCollection wAppCollection;
	private MainWindow mainMAppWindow;
	JButton button_save;
	JButton button_delete;
	JEditorPane editorPane;
	WebAppResponse testCurrentWebAppResponse;
	JButton button_test;
	private MasterKeyManager mkm = new MasterKeyManager();
	final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
	final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

	/**
	 * Create the panel.
	 */
	public OptionsPanelSSH(final SecureShellSSH sshservice,
			final WebAppCollection wapCollection, MainWindow mw) {

		this.webApp = sshservice;
		this.wAppCollection = wapCollection;
		this.mainMAppWindow = mw;

		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(null);
		setBounds(260, 11, 524, 545);

		// Name
		JLabel label_app_name = new JLabel(webApp.getName());
		label_app_name.setForeground(new Color(128, 0, 0));
		label_app_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_app_name.setBounds(10, 8, 504, 20);
		add(label_app_name);

		JLabel label_name = new JLabel("Name");
		label_name.setBounds(10, 39, 102, 14);
		add(label_name);

		final JTextField textField_name = new JTextField(webApp.getName());
		textField_name.setBounds(122, 36, 392, 20);
		add(textField_name);
		textField_name.setColumns(10);

		// Host
		JLabel label_host = new JLabel("Host");
		label_host.setBounds(10, 64, 102, 14);
		add(label_host);

		final JTextField textField_host = new JTextField(webApp.getServer());
		textField_host.setBounds(122, 61, 392, 20);
		add(textField_host);
		textField_host.setColumns(10);

		// Port
		JLabel label_port = new JLabel("Port");
		label_port.setBounds(10, 89, 102, 14);
		add(label_port);

		final JTextField textField_port = new JTextField(webApp.getPort());
		textField_port.setBounds(122, 86, 135, 20);
		add(textField_port);
		textField_port.setColumns(10);

		// Username
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 114, 102, 14);
		add(lblUsername);

		final JTextField textField_username = new JTextField(webApp.getUser());
		textField_username.setBounds(122, 111, 135, 20);
		add(textField_username);
		textField_username.setColumns(10);

		// Password
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 139, 102, 14);
		add(lblPassword);

		final JPasswordField textField_passwd = new JPasswordField(mkm.getDecryptedPassword(webApp.getPassword())); //Decrypted password
		textField_passwd.setBounds(122, 136, 135, 20);
		add(textField_passwd);
		textField_passwd.setColumns(10);

		// SendString
		JLabel lbl_sendString = new JLabel("Send string");
		lbl_sendString.setBounds(10, 164, 102, 14);
		add(lbl_sendString);

		final JTextField textField_sendString = new JTextField(
				webApp.getCmdToExec());
		textField_sendString.setBounds(122, 161, 392, 20);
		add(textField_sendString);
		textField_sendString.setColumns(10);

		// Expected Data
		JLabel lbl_expectedResponse = new JLabel("Expected Response");
		lbl_expectedResponse.setBounds(10, 189, 102, 14);
		add(lbl_expectedResponse);

		final JTextField expectedResponse = new JTextField(
				webApp.getExpectedString());
		expectedResponse.setBounds(122, 186, 392, 20);
		add(expectedResponse);
		expectedResponse.setColumns(10);

		// Execute on offline
		JLabel lblExecuteonoffline = new JLabel("Execute on offline");
		lblExecuteonoffline.setBounds(10, 214, 102, 14);
		add(lblExecuteonoffline);

		final JTextField textField_executeOnOffline = new JTextField(
				webApp.getExecuteOnOffline());
		textField_executeOnOffline.setBounds(122, 211, 392, 20);
		add(textField_executeOnOffline);
		textField_executeOnOffline.setColumns(10);

		// Groups
		JLabel label_groups = new JLabel("Groups");
		label_groups.setBounds(10, 239, 102, 14);
		add(label_groups);

		final JTextField textField_groups = new JTextField(
				webApp.getGroupsListString());
		textField_groups.setToolTipText("Groups separated by comma \",\" ");
		textField_groups.setBounds(122, 236, 392, 20);
		add(textField_groups);
		textField_groups.setColumns(10);

		// Save button
		button_save = new JButton("Save");
		button_save.setBounds(10, 511, 89, 23);
		add(button_save);

		button_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button_save.setEnabled(false);

				String new_name = textField_name.getText();
				String new_host = textField_host.getText();
				String new_port = textField_port.getText();
				String new_username = textField_username.getText();
				String new_passwd = new String(textField_passwd.getPassword());
				String new_sendString = textField_sendString.getText();
				String new_expectedResponse = expectedResponse.getText();
				String new_executeOnOffline = textField_executeOnOffline.getText();
				String new_groups = textField_groups.getText();

				if (new_name.equals("") || new_host.equals("")
						|| new_port.equals("")) {
					SimplePopup p = new SimplePopup(
							"Please fill in all required fields!");
					p.show();
					button_save.setEnabled(true);
				} else {
					boolean needHardRefresh = false;
					if (!webApp.getName().equalsIgnoreCase(new_name)) {
						needHardRefresh = true;
					}

					webApp.setName(new_name);
					webApp.setServer(new_host);
					webApp.setPort(new_port);
					webApp.setUser(new_username);
					webApp.setPasswd(new_passwd);
					webApp.setCmdToExec(new_sendString);
					webApp.setExpectedString(new_expectedResponse);
					webApp.setExecuteOnOffline(new_executeOnOffline);

					new_groups = new_groups.replaceAll(" ", ",");
					webApp.addGroups(new_groups);
					mainMAppWindow.refreshGroupsList();
					if (needHardRefresh) {
						mainMAppWindow.refresh();
					}
					wapCollection.saveConfigXMLFile();
					SimplePopup p = new SimplePopup("Saved!");
					webApp.setActive(true);
					button_test.setEnabled(true);
					p.show();
				}
				button_save.setEnabled(true);
				webApp.writeWebAppVisualizationDataFile();
			}
		});

		// Delete button
		button_delete = new JButton("Delete");
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogBoxDeleteResetApp dappw = new DialogBoxDeleteResetApp(mainMAppWindow, wAppCollection, webApp, false);
				dappw.showUp();
				setCursor(DEFAULT_CURSOR);
			}
		});
		button_delete.setBounds(425, 511, 89, 23);
		add(button_delete);

		// Test button
		button_test = new JButton("Test");
		button_test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Disable components
				Component[] components = getComponents();
				for (int i = 0; i < components.length; i++) {
					components[i].setEnabled(false);
				}
				button_test.setText("Running...");
				button_test.setEnabled(false);
				setCursor(WAIT_CURSOR);

				testCurrentWebAppResponse = webApp.checkWebAppStatus();

				SimplePopup p;
				TestResultWindow trw;
				if (testCurrentWebAppResponse.isOnline()) {
					p = new SimplePopup("Result for " + webApp.getName()
							+ "\n\nStatus: Online");
					p.showMsg();
				} else {
					trw = new TestResultWindow("Result for " + webApp.getName()
							+ ": FAILED!", webApp.getCurrentResponse());
					trw.showUp();
				}

				testCurrentWebAppResponse = null;

				// Enable components
				button_test.setText("Test");
				button_test.setEnabled(true);
				for (int i = 0; i < components.length; i++) {
					components[i].setEnabled(true);
				}
				button_save.setEnabled(true);
				setCursor(DEFAULT_CURSOR);

			}
		});
		button_test.setBounds(109, 511, 89, 23);
		add(button_test);

		// Reset button
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogBoxDeleteResetApp dappw = new DialogBoxDeleteResetApp(mainMAppWindow, wAppCollection, webApp, true);
				dappw.showUp();
				setCursor(DEFAULT_CURSOR);	
			}
		});
		btnReset.setBounds(324, 511, 91, 23);
		add(btnReset);

		if (!webApp.isActive()) {
			button_test.setEnabled(false);
		}
	}

	public OptionsPanelSSH() {
	}

}
