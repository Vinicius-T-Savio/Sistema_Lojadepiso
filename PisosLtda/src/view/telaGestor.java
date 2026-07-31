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
import connection.post;
import pisos.main;
public class telaGestor {

	private JFrame frmTopdecor;
	private usaPost usaPost;
	private post post;
	static main rand = new main();
	public JFrame getFrame() {
	    return this.frmTopdecor;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaGestor window = new telaGestor();
					window.frmTopdecor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public telaGestor() {
		initialize();
	}

	private void initialize() {
		frmTopdecor = new JFrame();
		frmTopdecor.setBounds(100, 100, 569, 344);
		frmTopdecor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTopdecor.getContentPane().setLayout(null);
		
		JLabel lblGestor = new JLabel("Gestor");
		lblGestor.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblGestor.setBounds(223, 20, 102, 22);
		frmTopdecor.getContentPane().add(lblGestor);
		
		JButton btnResumo = new JButton("Resumo Geral"); 
		btnResumo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnResumo.setBounds(209, 76, 128, 23);
		frmTopdecor.getContentPane().add(btnResumo);
		
		btnResumo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usaPost.mostrarGestor(); 
			}
		});
		
		JButton btEstoque = new JButton("Em estoque");
		btEstoque.setFont(new Font("Tahoma", Font.BOLD, 12));
		btEstoque.setBounds(209, 133, 128, 23);
		frmTopdecor.getContentPane().add(btEstoque);
		btEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.usaPost.crudEstoque("tudo", "", 0, "", 0, "", "", "", "", 0, 0);
			}
		});

		JButton btGerenciar = new JButton("Gerenciar ");
		btGerenciar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btGerenciar.setBounds(209, 194, 128, 22);
		frmTopdecor.getContentPane().add(btGerenciar);
		btGerenciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usaPost = new usaPost();
				post = new post();
				String[] alts = {"adicionar produto", "alterar produto", "deletar produto", "visualizar usuarios","adicionar usuario", "alterar usuario", "deletar usuario"};
				String alternativas = (String) JOptionPane.showInputDialog(frmTopdecor, "escolha a operaçao", "gerenciar sistema",JOptionPane.QUESTION_MESSAGE, null, alts, alts[0]);
				int utilizaRand;
				String nome, tipo, tamanho, marca, encaixe, senha, cargo;
				int id_tabela;
				int qtd_fisica;
				double preco;
				double volume;
				switch(alternativas) {
					case "adicionar produto":
						nome = JOptionPane.showInputDialog(frmTopdecor, "Digite o nome do produto a ser adicionado:");
						qtd_fisica = Integer.parseInt(JOptionPane.showInputDialog(frmTopdecor, "Digite a quantidade desse produto:"));
						String[] tipos = {"piso laminado","piso vinílico", "piso floorest", "persiana", "rodapé"};
						tipo = (String) JOptionPane.showInputDialog(frmTopdecor, "escolha o tipo de produto", "Tipos", JOptionPane.INFORMATION_MESSAGE, null, tipos, tipos[0]);
						tamanho = JOptionPane.showInputDialog(frmTopdecor, "Digite o tamanho do produto em milimetros (Largura x Altura x Peças por caixa):");
						volume = Double.parseDouble(JOptionPane.showInputDialog(frmTopdecor, "Digite o tamanho do produto em metros quadrados(XX.XX):"));
						marca = JOptionPane.showInputDialog(frmTopdecor, "Digite a marca do produto:");
						String[] encaixes = {"CLICK", "COLADO"};
						encaixe = (String) JOptionPane.showInputDialog(frmTopdecor, "escolha o tipo de encaixe:", "Encaixe", JOptionPane.INFORMATION_MESSAGE, null, encaixes, encaixes[0]);
						preco = Double.parseDouble(JOptionPane.showInputDialog(frmTopdecor, "Digite o preço do produto:"));
						service.usaPost.crudEstoque("insert", "", 0, nome, qtd_fisica, tipo,tamanho, marca, encaixe, preco, volume);
					break;
					case "alterar produto":
						id_tabela = Integer.parseInt(JOptionPane.showInputDialog(frmTopdecor, "Digite o id do produto:"));
						String[] escolhas = {"nome", "quantidade", "tipo", "tamanho", "marca", "encaixe", "preco", "volume"};
						String escolha = (String) JOptionPane.showInputDialog(frmTopdecor, "escolha o tipo de encaixe:", "Encaixe", JOptionPane.INFORMATION_MESSAGE, null, escolhas, escolhas[0]);
						service.usaPost.crudEstoque("update", escolha, id_tabela, "", 0, "", "", "", "", 0, 0);
					break;
					case "deletar produto":
						id_tabela = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id do produto:"));
						service.usaPost.crudEstoque("delete", "", id_tabela, "", 0, "", "", "", "", 0, 0);
					break;
					case "visualizar usuarios":
						service.usaPost.crudUser("select", 0, "", "", "", "");
						break;
					case "adicionar usuario":	
						utilizaRand = rand.gerarId("usuario");
						nome = JOptionPane.showInputDialog(frmTopdecor, "Digite o nome do usuario a ser adicionado:");
						senha = JOptionPane.showInputDialog(frmTopdecor, "Digite a senha do usuario a ser adicionada:");
						//fazer metodo que checar senha, fazer isso também no cadastro
						cargo = JOptionPane.showInputDialog(frmTopdecor, "Digite o cargo do usuario a ser adicionado:");
						service.usaPost.crudUser("insert", utilizaRand, nome, senha, cargo, "");
					break;
					case "alterar usuario":
						id_tabela = Integer.parseInt(JOptionPane.showInputDialog(frmTopdecor, "Digite o id do usuario a ser alterado:"));
						String[] alts3 = {"nome", "senha", "cargo"};
						alternativas = (String) JOptionPane.showInputDialog(frmTopdecor, "escolha a operaçao", "gerenciar sistema",JOptionPane.QUESTION_MESSAGE, null, alts3, alts3[0]);
						service.usaPost.crudUser("update", id_tabela, "", "", "", alternativas);
					break;
					case "deletar usuario":
					    String inputId = JOptionPane.showInputDialog(frmTopdecor, "Digite o id do usuario a ser deletado:");
					    
					    if (inputId == null || inputId.trim().isEmpty()) {
					        break;
					    }
					    
					    try {
					        id_tabela = Integer.parseInt(inputId);
					    } catch (NumberFormatException e1) {
					        JOptionPane.showMessageDialog(frmTopdecor, "ID inválido! Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
					        break; 
					    }

					    String[] alts4 = {"vendedor", "estoquista", "financeiro", "gestor"};
					    alternativas = (String) JOptionPane.showInputDialog(frmTopdecor, "escolha a operaçao", "gerenciar sistema", JOptionPane.QUESTION_MESSAGE, null, alts4, alts4[0]);
					    
					    if (alternativas == null) {
					    	
					        JOptionPane.showMessageDialog(frmTopdecor, "Exclusão cancelada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					        break; 
					    }
					    
					    service.usaPost.crudUser("delete", id_tabela, "", "", alternativas, "");
					    break;
					default:
					break;
				}
			}
		});

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
}