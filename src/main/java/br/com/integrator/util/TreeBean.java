package br.com.integrator.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class TreeBean implements Serializable {  

	private static final long serialVersionUID = 1L;

	private TreeNode root;  
  
    private TreeNode selectedNode;  
  
    public TreeBean() {  
        root = new DefaultTreeNode("Root", null);  
        TreeNode node0 = new DefaultTreeNode("Categoria", root);  
        TreeNode node1 = new DefaultTreeNode("Produto", root);  
        TreeNode node2 = new DefaultTreeNode("Compras", root);  
        TreeNode node3 = new DefaultTreeNode("Sair", root);  

        TreeNode node00 = new DefaultTreeNode("Cadastrar", node0);  
        TreeNode node01 = new DefaultTreeNode("Exibir", node0);  
        TreeNode node10 = new DefaultTreeNode("Cadastrar", node1);  
        TreeNode node11 = new DefaultTreeNode("Exibir", node1);  
        TreeNode node20 = new DefaultTreeNode("Exibir", node2);  
        
        
        
      /*  TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);  
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);  
  
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);  
        TreeNode node11 = new DefaultTreeNode("Node 1.1", node1);  
  
        TreeNode node000 = new DefaultTreeNode("Node 0.0.0", node00);  
        TreeNode node001 = new DefaultTreeNode("Node 0.0.1", node00);  
        TreeNode node010 = new DefaultTreeNode("Node 0.1.0", node01);  
  
        TreeNode node100 = new DefaultTreeNode("Node 1.0.0", node10);  */
    }  
  
    public TreeNode getRoot() {  
        return root;  
    }  
  
    public TreeNode getSelectedNode() {  
        return selectedNode;  
    }  
  
    public void setSelectedNode(TreeNode selectedNode) {  
        this.selectedNode = selectedNode;  
    }  
      
    public void displaySelectedSingle() {  
        if(selectedNode != null) {  
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());  
  
            FacesContext.getCurrentInstance().addMessage(null, message);  
        }  
    }  
  
    public void deleteNode() {  
        selectedNode.getChildren().clear();  
        selectedNode.getParent().getChildren().remove(selectedNode);  
        selectedNode.setParent(null);  
          
        selectedNode = null;  
    }  
}  