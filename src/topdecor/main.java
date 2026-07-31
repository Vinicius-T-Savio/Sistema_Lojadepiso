package topdecor;

import java.awt.EventQueue;
import java.util.Random;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.post;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import view.telaCadastro;
import view.telaLogin;
public class main {

	private JFrame frmTopdecor;
	private static post post = new post();
	private static ResultSet rs;
	private telaCadastro cad;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frmTopdecor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTopdecor = new JFrame();
		frmTopdecor.setBounds(100, 100, 569, 344);
		frmTopdecor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTopdecor.getContentPane().setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(91, 10, 369, 26);
		frmTopdecor.getContentPane().add(toolBar);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaLogin log = new telaLogin(frmTopdecor);
				log.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnEntrar);
		
		JButton btnCadastro = new JButton("Cadastrar-se");
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaCadastro cad = new telaCadastro();
				cad.getFrame().setVisible(true);
			}
		});
		toolBar.add(btnCadastro);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		toolBar.add(btnSair);
	}
	public static int gerarId(String nomeTabela) {
	    Random rd = new Random();
	    int id = rd.nextInt(9000);
	    try {
	        if(post.conC()) {
	            String sqlSelect = "SELECT * FROM usuario WHERE id_user = ?";
	            PreparedStatement ps = post.con.prepareStatement(sqlSelect);
	            ps.setInt(1, id);
	            rs = ps.executeQuery();
	            
	            while(rs.next()) {
	                int encontrado = rs.getInt(1);
	                if(encontrado == id) {
	                    id = rd.nextInt(9000); // Gera um novo se já existir
	                }
	            }
	            rs.close();
	            ps.close();
	        }
	    } catch(SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro na tabela " + nomeTabela + ": " + e.getMessage());
	    }
	    return id;
	}
	public static int Id(String nomeTabela) {
	    Random rd = new Random();
	    int id = rd.nextInt(9000);
	    try {
	        if(post.conC()) {
	        	String sql = "SELECT * FROM ";
	        	switch(nomeTabela) {
	        	case "agendamento":
	        		sql+="agendamento WHERE id_agendamento = ?";
	        	break;
	        	case "orcamento":
	        		sql+="orcamento WHERE id_orcamento = ?";
	        	break;
	        	case "pedido":
	        		sql+="pedido WHERE id_pedido = ?";
	        	break;
	        	case "cliente":
	        		sql+="cliente WHERE id_cliente = ?";
	        	}
	            PreparedStatement ps = post.con.prepareStatement(sql);
	            ps.setInt(1, id);
	            rs = ps.executeQuery();
	            
	            while(rs.next()) {
	                int encontrado = rs.getInt(1);
	                if(encontrado == id) {
	                    id = rd.nextInt(9000); // Gera um novo se já existir
	                }
	            }
	            rs.close();
	            ps.close();
	        }
	    } catch(SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro na tabela " + nomeTabela + ": " + e.getMessage());
	    }
	    return id;
	}
	
	public boolean buscarCargo(String cargo, int id) {
		try {
			if(post.conC()) {
				String sqlSelect = "SELECT cargo from usuario WHERE id_user = ?";
				PreparedStatement ps = post.con.prepareStatement(sqlSelect);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				boolean temID;
				while(rs.next()) {
				}
				rs.close();
				ps.close();
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return true;
	}
}
