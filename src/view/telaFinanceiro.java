package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import service.usaPost;

public class telaFinanceiro {

	private JFrame frmTopdecor;
	private usaPost post = new usaPost();
    
	public JFrame getFrame() {
		return this.frmTopdecor;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaFinanceiro window = new telaFinanceiro();
					window.frmTopdecor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public telaFinanceiro() {
		initialize();
	}

	private void initialize() {
		frmTopdecor = new JFrame();
		frmTopdecor.setBounds(100, 100, 569, 344);
		frmTopdecor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTopdecor.getContentPane().setLayout(null);
		
		JLabel lblFinanceiro = new JLabel("Financeiro");
		lblFinanceiro.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinanceiro.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblFinanceiro.setBounds(226, 24, 102, 22);
		frmTopdecor.getContentPane().add(lblFinanceiro);

		JButton btnAnalisarPedidos = new JButton("Analisar pedidos");
		btnAnalisarPedidos.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAnalisarPedidos.setBounds(195, 90, 163, 23);
		btnAnalisarPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] opcoes = {"visualizar todos", "pesquisar", "aprovar", "desaprovar"};
				String status = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha a ação para o Pedido:", "Analisar Pedidos",
						JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
				int idPed = 0; 
				if (status == null) return;

				try {
					switch (status) {
						case "visualizar todos":
							post.pesquisarPedido(idPed, status);
						break;
						case "pesquisar":
							idPed = Integer.parseInt(pedirDados("Digite o ID do Pedido:")); 
							post.pesquisarPedido(idPed, status);
						break;
						case "aprovar":
							idPed = Integer.parseInt(pedirDados("Digite o ID do Pedido:")); 
							post.aprovarPedido(idPed, status);
						break;
						case "desaprovar":
							idPed = Integer.parseInt(pedirDados("Digite o ID do Pedido:")); 
							post.aprovarPedido(idPed, status);
						break;
					}
					
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frmTopdecor, "Erro: O ID do pedido precisa ser um número inteiro válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frmTopdecor.getContentPane().add(btnAnalisarPedidos);
		
		JButton btnFazerRelatrios = new JButton("Fazer relatórios");
		btnFazerRelatrios.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnFazerRelatrios.setBounds(195, 141, 163, 23);
		btnFazerRelatrios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "implementacao a ser realizada no projeto!");
			}
		});
		frmTopdecor.getContentPane().add(btnFazerRelatrios);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSair.setBounds(443, 274, 102, 23);
		frmTopdecor.getContentPane().add(btnSair);
	}

	private String pedirDados(String mensagem) {
		while (true) {
			String dado = JOptionPane.showInputDialog(frmTopdecor, mensagem);
			if (dado == null) {
				return null;
			}
			if (!dado.trim().isEmpty()) {
				return dado.trim();
			}
			JOptionPane.showMessageDialog(frmTopdecor, "O campo não pode ficar vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
}