package view;

import java.awt.EventQueue;
import java.lang.NumberFormatException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import service.usaPost;
import java.util.Random;
import java.util.ArrayList;

public class telaVendas {
    
    private JFrame frmTopdecor;
    private usaPost post = new usaPost();
    private static Random rd = new Random();
    
    public JFrame getFrame() {
        return this.frmTopdecor;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    telaVendas window = new telaVendas();
                    window.frmTopdecor.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showConfirmDialog(null, "arvore3");                
                    }
            }
        });
    }

    public telaVendas() {
        initialize();
    }

    private void initialize() {
        frmTopdecor = new JFrame();
        frmTopdecor.setBounds(100, 100, 569, 344);
        frmTopdecor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTopdecor.getContentPane().setLayout(null);
        
        JLabel lblVendas = new JLabel("Vendas");
        lblVendas.setHorizontalAlignment(SwingConstants.CENTER);
        lblVendas.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        lblVendas.setBounds(230, 21, 102, 22);
        frmTopdecor.getContentPane().add(lblVendas);
        
        JButton btnAgendamento = new JButton("Agendamento");
        btnAgendamento.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAgendamento.setBounds(215, 83, 128, 23);
        btnAgendamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] opcoes = {"agendar", "alterar", "cancelar", "visualizar todos"};
                String opcao = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha a operação:", "Agendamento",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                
                if (opcao == null) return;

                if (opcao.equals("visualizar todos")) {
                    usaPost.agendarInst("select", 0, "", "", "", 0, 0);
                } else if (opcao.equals("alterar")) {
                    String idStr = pedirDados("Digite o ID do Agendamento que deseja atualizar:");
                    if (idStr == null) return;
                    int idAgend = Integer.parseInt(idStr);
                    
                    String data = pedirDados("Digite a Nova Data (AAAA-MM-DD):");
                    if (data == null) return;
                    
                    usaPost.agendarInst("update", idAgend, data.trim(), "", "", 0, 0);
                } else if (opcao.equals("cancelar")) {
                    int idStr = Integer.parseInt(pedirDados("Digite o ID do Agendamento que deseja deletar:"));
                    usaPost.agendarInst("delete", idStr, "", "", "", 0, 0);
                } else {
                    String data = pedirDados("Digite a Data (AAAA-MM-DD):");
                    if (data == null) return;
                    
                    String horario = pedirDados("Digite o Horário (HH:MM):");
                    if (horario == null) return;
                    
                    String status = pedirDados("Digite a cor/status:");
                    if (status == null) return;
                    
                    String fkPedStr = pedirDados("Digite o ID do Pedido (Inteiro):");
                    if (fkPedStr == null) return;
                    int fkPedido = Integer.parseInt(fkPedStr);
                    
                    String fkCliStr = pedirDados("Digite o ID do Cliente (Inteiro):");
                    if (fkCliStr == null) return;
                    int fkCliente = Integer.parseInt(fkCliStr);

                    usaPost.agendarInst("insert", rd.nextInt(10000), data.trim(), horario.trim(), status.trim(), fkPedido, fkCliente);
                }
            }
        });
        frmTopdecor.getContentPane().add(btnAgendamento);
        
        JButton btnOramento = new JButton("Orçamento");
        btnOramento.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnOramento.setBounds(215, 116, 128, 23);
        btnOramento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String alts[] = {"gerar orcamento", "visualizar orcamentos", "atualizar orcamento", "deletar orcamentos"};
                    String alternativas = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha a operação", "Orcamentos", JOptionPane.QUESTION_MESSAGE, null, alts, alts[0]);
                    
                    if (alternativas == null) return;
                    
                    int id = rd.nextInt(9000);
                    int id_cliente = 0;
                    int id1 = 0;
                    String nome = "";
                    String end = "";
                    String tel = "";
                    String nomeM = "";
                    String id_prod = "";
                    String qtd = "";
                    double total = 0;
                    int j = 0;
                    ArrayList<Integer> calcId = new ArrayList<>();

                    switch(alternativas) {
                        case "gerar orcamento":
                            nome = JOptionPane.showInputDialog(null, "Digite o nome do cliente:");
                            if (nome == null) return;
                            end = JOptionPane.showInputDialog(null, "Digite o endereco do cliente:");
                            if (end == null) return;
                            tel = JOptionPane.showInputDialog(null, "Digite o telefone do cliente:");
                            if (tel == null) return;
                            
                            String qtsMat = JOptionPane.showInputDialog(null, "Quantos materiais voce quer na obra:");
                            if (qtsMat == null) return;
                            j = Integer.parseInt(qtsMat); 
                            
                            for(int i = 0; i < j; i++) {
                                String input = JOptionPane.showInputDialog(null, "Digite o nome do material:");
                                if (input == null) return;
                                String idProdInput = JOptionPane.showInputDialog(null, "Digite o id do produto:");
                                if (idProdInput == null) return;
                                int input1 = Integer.parseInt(idProdInput);
                                String qtdInput = JOptionPane.showInputDialog(null, "Digite a quantidade do [" + input + "]:");
                                if (qtdInput == null) return;
                                int input2 = Integer.parseInt(qtdInput);
                                
                                nomeM += input;
                                id_prod += input1;
                                qtd += input2;
                                calcId.add(input1);
                                
                                if(i < j - 1) {
                                    nomeM += ",";
                                    id_prod += ",";
                                    qtd += ",";
                                }
                            }
                            for(int calc : calcId) {
                                double preco = post.calcularProd(calc);
                                total += preco;
                            }
                            usaPost.gerarOrcamento("insert", "", id, nome, rd.nextInt(9000), end, tel, nomeM, qtd, total);
                            break;

                        case "visualizar orcamentos":
                            usaPost.gerarOrcamento("select", "", id, "", 12, "", "", "", "", 0);
                            break;

                        case "atualizar orcamento": 
                            String idUpdateStr = JOptionPane.showInputDialog(null, "Digite o ID do orçamento que deseja atualizar:");
                            if (idUpdateStr == null) return;
                            id = Integer.parseInt(idUpdateStr);
                            String[] alts1 = {"nome do cliente", "endereço do cliente", "telefone do cliente", "nome dos materiais", "quantidade dos materiais", "valor total"};
                            String alternativas1 = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha a operação", "Atualizar orcamento", JOptionPane.QUESTION_MESSAGE, null, alts1, alts1[0]);
                            
                            if (alternativas1 == null) return;

                            switch(alternativas1) {
                                case "nome do cliente":
                                	idUpdateStr = JOptionPane.showInputDialog(null, "Digite o ID do cliente que deseja atualizar:");
                                	id1 = Integer.parseInt(idUpdateStr);
                                    nome = JOptionPane.showInputDialog(null, "Digite o nome do cliente:");
                                    if (nome == null) return;
                                    break;
                                case "endereço do cliente":
                                	idUpdateStr = JOptionPane.showInputDialog(null, "Digite o ID do cliente que deseja atualizar:");
                                	id1 = Integer.parseInt(idUpdateStr);
                                    end = JOptionPane.showInputDialog(null, "Digite o novo endereco do cliente:");
                                    if (end == null) return;
                                    break;
                                case "telefone do cliente":
                                	idUpdateStr = JOptionPane.showInputDialog(null, "Digite o ID do cliente que deseja atualizar:");
                                	id1 = Integer.parseInt(idUpdateStr);
                                    tel = JOptionPane.showInputDialog(null, "Digite o novo telefone do cliente:");
                                    if (tel == null) return;
                                    break;
                                case "nome dos materiais":
                                    String qtsMatUp = JOptionPane.showInputDialog(null, "Quantos materiais voce quer na obra:");
                                    if (qtsMatUp == null) return;
                                    j = Integer.parseInt(qtsMatUp);
                                    for(int i = 0; i < j; i++) {
                                        String input = JOptionPane.showInputDialog(null, "Digite o nome do material:");
                                        if (input == null) return;
                                        String idProdInput = JOptionPane.showInputDialog(null, "Digite o id do produto:");
                                        if (idProdInput == null) return;
                                        double input1 = Double.parseDouble(idProdInput);
                                        String qtdInput = JOptionPane.showInputDialog(null, "Digite a quantidade do [" + input + "]:");
                                        if (qtdInput == null) return;
                                        int input2 = Integer.parseInt(qtdInput);
                                        
                                        nomeM += input;
                                        id_prod += input1;
                                        qtd += input2;
                                        double preco = post.calcularProd(input1);
                                        total += preco;
                                        if(i < j - 1) {
                                            nomeM += ",";
                                            id_prod += ",";
                                            qtd += ",";
                                        }
                                    }
                                    break;
                                case "quantidade dos materiais":
                                    qtd = JOptionPane.showInputDialog(null, "Digite as novas quantidades separadas por vírgula:");
                                    if (qtd == null) return;
                                    break;
                                case "valor total":
                                    String totalStr = JOptionPane.showInputDialog(null, "Digite o novo valor total:");
                                    if (totalStr == null) return;
                                    total = Double.parseDouble(totalStr);
                                    break;
                            }
                            usaPost.gerarOrcamento("update", alternativas1, id, nome, id1, end, tel, nomeM, qtd, total);
                            break;

                        case "deletar orcamentos":
                            String idDelStr = JOptionPane.showInputDialog(null, "Digite o ID do orçamento que deseja deletar:");
                            if (idDelStr == null) return;
                            id = Integer.parseInt(idDelStr);
                            usaPost.gerarOrcamento("delete", "", id, "", 0, "", "", "", "", 0);
                            break;

                        default:
                            break;
                    }
                } catch(NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Arvore");
                }
            }
        });
        frmTopdecor.getContentPane().add(btnOramento);
        
        JButton btnPedidos = new JButton("Pedidos");
        btnPedidos.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPedidos.setBounds(215, 149, 128, 23);
        btnPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] opcoes = {"gerar pedido", "visualizar todos", "atualizar pedido", "deletar pedido"};
                String opcao = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha a operação:", "Pedidos",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                if (opcao == null) return;

                if (opcao.equals("visualizar todos")) {
                    post.gerarPedido("select", "", 0, 0, false, false, 0, "2026-06-18", 0, "");
                }else if(opcao.equals("gerar pedido")){
                    try {
                        int vendedor = Integer.parseInt(pedirDados("Digite o ID do Vendedor:"));
                        int cliente = Integer.parseInt(pedirDados("Digite o ID do Cliente:"));
                        double valorTotal = Double.parseDouble(pedirDados("Digite o Valor Total:"));
                        String material = pedirDados("Digite o Nome do Material:");
                        
                        LocalDate today = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        int idPedidoGerado = rd.nextInt(10000);
                        String dateFormatado = today.format(formatter);
                        post.gerarPedido("insert", "",idPedidoGerado, vendedor, false, false, cliente, dateFormatado, valorTotal, material);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frmTopdecor, "Erro: Digite valores numéricos válidos.");
                    } catch (NullPointerException ex) {
                    }
                }
                else if(opcao.equals("atualizar pedido")) {
                	try {
                		
                		int vende = 0;
                		int cli = 0;
                		String data = "";
                		String nomeMaterial = "";
                		double valorTotal = 0;
                		int id = Integer.parseInt(pedirDados("Digite o id do pedido"));
                		String[] alts = {"vendedor", "cliente", "data", "nome do material","valor total"};
                		String alternativas = (String) JOptionPane.showInputDialog(frmTopdecor, "Escolha o campo a ser modificado:", "Pedidos", JOptionPane.QUESTION_MESSAGE, null, alts, alts[0]);
                		if(alternativas.equals("vendedor")) {
                			vende = Integer.parseInt(pedirDados("Digite o ID do Vendedor:"));
                		}else if(alternativas.equals("cliente")) {
                			cli = Integer.parseInt(pedirDados("Digite o ID do Cliente:"));
                		}else if(alternativas.equals("data")) {
                			data = pedirDados("Digite a nova data (AAAA-MM-DD):");
                		}else if(alternativas.equals("nome do material")) {
                			nomeMaterial = pedirDados("Digite o novo nome do material:");
                		}else{
                			valorTotal = Double.parseDouble(pedirDados("Digite o valor total(XX.XX):"));
                		}
                		post.gerarPedido("update", alternativas, id, vende, false, false, cli, data, valorTotal, nomeMaterial);
                	}catch(NumberFormatException e1) {
                		JOptionPane.showMessageDialog(btnPedidos, "Insira números dentro dos inputs que pedem números!");
                	}
                }
                else {
                	int id_pe = Integer.parseInt(pedirDados("Digite o ID do agendamento:"));
                	post.gerarPedido("delete", "", id_pe, id_pe, false, false, id_pe, opcao, id_pe,opcao);
                }
            }
        });
        frmTopdecor.getContentPane().add(btnPedidos);
        
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