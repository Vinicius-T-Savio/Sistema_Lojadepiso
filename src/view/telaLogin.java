package view;

import java.awt.EventQueue;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import service.usaPost;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class telaLogin {
	
	public JFrame getFrame() {
	    return this.frame;
	}
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private static usaPost dao = new usaPost();
	private static JFrame frmTopdecor;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaLogin window = new telaLogin(frmTopdecor);
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
	public telaLogin(JFrame frmTopdecor) {
		telaLogin.frmTopdecor = frmTopdecor;
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
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel.setBounds(150, 11, 128, 41);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuário");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(116, 96, 58, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(116, 117, 208, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(116, 148, 58, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(116, 168, 208, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		JComboBox<String[]> comboBoxCargo = new JComboBox();
		comboBoxCargo.setModel(new DefaultComboBoxModel(new String[] {"vendedor", "estoquista", "financeiro", "gestor"}));
		comboBoxCargo.setBounds(116, 63, 208, 22);
		frame.getContentPane().add(comboBoxCargo);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String usuario = textField.getText().trim();
					String senha = textField_1.getText().trim();
					String cargo = comboBoxCargo.getSelectedItem().toString();
					
					// 1. Valida se os campos estão vazios PRIMEIRO
					if(usuario.isEmpty() || senha.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Campo nome e/ou senha não preenchidos\nfavor preenchê-los!","Campo(s) vazio(s)", JOptionPane.INFORMATION_MESSAGE);
						return; // Para a execução aqui e não tenta ir ao banco
					}
					
					// 2. Valida se tem números no nome PRIMEIRO
					Pattern tipagem = Pattern.compile("\\d+");
					Matcher conversor = tipagem.matcher(usuario);
					if(conversor.find()) {
						JOptionPane.showMessageDialog(frame, "Não insira números no nome de seu usuário", "Números no nome", JOptionPane.INFORMATION_MESSAGE);
						return; // Para a execução aqui
					}
					
					// 3. Agora sim chama o banco com segurança usando a sua variável 'dao'
					if(dao.loginUser(usuario, senha, cargo)) {
						switch (cargo) {
							case "vendedor":
								telaVendas telaVendas = new telaVendas();
								telaVendas.getFrame().setVisible(true);
								break;
							
							case "estoquista":
								telaEstoque telaEstoque = new telaEstoque();
								telaEstoque.getFrame().setVisible(true);
								break;
								
							case "financeiro":
								telaFinanceiro telaFinanceiro = new telaFinanceiro();
								telaFinanceiro.getFrame().setVisible(true);
								break;
								
							case "gestor":
								telaGestor telaGestor = new telaGestor();
								telaGestor.getFrame().setVisible(true);
								break;
								
							default:
								System.out.println("Nenhum cargo válido escolhido.");
								return;
						}
						
						// Se o switch rodar com sucesso, fecha as janelas com as variáveis certas:
						if (frmTopdecor != null) {
							frmTopdecor.dispose(); // Fecha a tela Principal de trás (d minúsculo)
						}
						frame.dispose(); // Fecha a tela de Login atual
						
					} else {
						// Se o banco retornar falso (usuário/senha errados)
						JOptionPane.showMessageDialog(frame, "Usuário, senha ou cargo incorretos!", "Falha no Login", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (Exception ex) {
					// Mostra uma caixinha com o erro exato caso o banco de dados trave
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Erro na conexão com o banco: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEntrar.setBounds(150, 199, 128, 23);
		frame.getContentPane().add(btnEntrar);
		
		JLabel lblNewLabel_1_1 = new JLabel("Cargo");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(116, 46, 58, 20);
		frame.getContentPane().add(lblNewLabel_1_1);
	}
}

