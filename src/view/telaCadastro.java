package view;

import java.awt.EventQueue;
import java.util.random.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import connection.post;
import service.usaPost;
import topdecor.main;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.lang.NumberFormatException;
public class telaCadastro {

	public JFrame getFrame() {
	    return this.frame;
	}
	private static usaPost Dao = new usaPost();
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private static Random rd = new Random();
	private main principal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaCadastro window = new telaCadastro();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public telaCadastro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCadastro = new JLabel("Cadastro");
		lblCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastro.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblCadastro.setBounds(151, 11, 102, 22);
		frame.getContentPane().add(lblCadastro);
		
		JComboBox comboBoxCargo = new JComboBox();
		comboBoxCargo.setModel(new DefaultComboBoxModel(new String[] {"vendedor", "estoquista", "financeiro", "gestor"}));
		comboBoxCargo.setBounds(103, 75, 208, 22);
		frame.getContentPane().add(comboBoxCargo);
		
		JLabel lblNewLabel = new JLabel("Selecione seu cargo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(103, 50, 150, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(103, 135, 208, 20);
		frame.getContentPane().add(textField);
		
		JLabel lblNomeDeUsurio = new JLabel("Nome de usuário:");
		lblNomeDeUsurio.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNomeDeUsurio.setBounds(103, 110, 150, 14);
		frame.getContentPane().add(lblNomeDeUsurio);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(103, 193, 208, 20);
		frame.getContentPane().add(textField_1);
		
		JLabel lblDigiteUmaSenha = new JLabel("Digite uma senha:");
		lblDigiteUmaSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDigiteUmaSenha.setBounds(103, 168, 150, 14);
		frame.getContentPane().add(lblDigiteUmaSenha);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty()) {
					Dao.crudUser("insert", rd.nextInt(9000), textField.getText(),  textField_1.getText(), comboBoxCargo.getSelectedItem().toString(), "");
					frame.dispose();
					}else {
						JOptionPane.showMessageDialog(frame, "Campo nome e/ou senha não preenchidos,\n favor preenchao(s)","Campo(s) vazio(s)", JOptionPane.INFORMATION_MESSAGE);
					}
					Pattern tipagem = Pattern.compile("\\d+");
					Matcher conversor = tipagem.matcher(textField.getText());
					if(conversor.find()) {
						JOptionPane.showMessageDialog(frame, "Não insira números no nome de seu usuário", "Números no nome", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());;
				}
				}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCadastrar.setBounds(143, 224, 128, 23);
		frame.getContentPane().add(btnCadastrar);
	}
}
