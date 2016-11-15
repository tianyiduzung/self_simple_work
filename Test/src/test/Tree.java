package test;

class TreeNode{  
    int val=0;  
    TreeNode left=null;  
    TreeNode right=null;  
    public TreeNode(int val){  
        this.val=val;  
    }  
}  
public class Tree {  
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {  
        boolean result=false;  
        if(root1!=null&&root2!=null){  
            if(root1.val==root2.val){  
                result=DoesTreeAHaveTreeB(root1,root2);  
            }  
            if(result==false){  
                result=HasSubtree(root1.left,root2);  
            }  
            if(result==false){  
                result=HasSubtree(root1.right,root2);  
            }  
        } 
       return result;
    }  
    public boolean DoesTreeAHaveTreeB(TreeNode root1,TreeNode root2){  
        if(root2==null)return true;  
        if(root1==null)return false;  
        if(root1.val!=root2.val)return false;  
        return DoesTreeAHaveTreeB(root1.left,root2.left)&&DoesTreeAHaveTreeB(root1.right,root2.right);  
    } 
    public static void main(String[] args) {
		TreeNode a=new TreeNode(1);
		TreeNode b=new TreeNode(2);
		Tree t=new Tree();
		boolean result;
		result=t.HasSubtree(a, b);
		System.out.println(result);
	}
} 