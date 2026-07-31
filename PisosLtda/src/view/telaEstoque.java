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
import java.util.Random;
import service.usaPost;

public class telaEstoque {

    private JFrame frmTopdecor;
    private Random rd;
    public JFrame getFrame() {
        return this.frmTopdecor;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    telaEstoque window = new telaEstoque();
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
    public telaEstoque() {
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
        
        JLabel lblNewLabel = new JLabel("Estoque");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(231, 24, 102, 22);
        frmTopdecor.getContentPane().add(lblNewLabel);
        JButton btnEmEstoque = new JButton("Em estoque");
        btnEmEstoque.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnEmEstoque.setBounds(221, 111, 126, 23);
        btnEmEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] opcoes = {"visualizar estoque", "inserir novo produto"};
                String opcao = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha a operação de Estoque:", "Gerenciar Estoque",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                
                if (opcao == null) return;

                if (opcao.equals("visualizar estoque")) {
                    
                        usaPost.crudEstoque("tudo", "", 0, "", 0, "", "", "", "", 0, 0);
                    
                } else if (opcao.equals("inserir novo produto")) {
                    try {
                        String nome = pedirDados("Digite o Nome do produto:");
                        

                        int qtd_fisica = Integer.parseInt(pedirDados("Digite a Quantidade Física (Inteiro):"));
                        
                        String tipo = pedirDados("Digite o Tipo:");
                        

                        String tamanho = pedirDados("Digite o Tamanho:");
                        
                        
                        double volume = Float.parseFloat(pedirDados("Digite o volume por caixa (Ex: 2.00):"));
                        
                        String marca = pedirDados("Digite a Marca:");
                        

                        String encaixe = pedirDados("Digite o tipo de Encaixe:");
                        
                        
                        double preco = Float.parseFloat(pedirDados("Digite o Preço (Ex: 89.90):"));
                        
                        usaPost.crudEstoque("insert", "", 0, nome, qtd_fisica, tipo, tamanho, marca, encaixe, preco, volume);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frmTopdecor, "Erro: Insira valores numéricos válidos para Quantidade, Volume e Preço.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException ex) {
                       JOptionPane.showMessageDialog(frmTopdecor, "Erro: Insira valores, valor nulo enviado!");
                    }
                }
            }
        });
        frmTopdecor.getContentPane().add(btnEmEstoque);
        
        // --- BOTÃO SAIR ---
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

