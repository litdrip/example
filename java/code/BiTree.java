package tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 二叉树
 * 支绍昆 2018/7/21 0021
 */
public class BiTree {

    private BiNode root;

    //添加节点
    public void add(int value){

        // 1.根不存在时,新建节点,并结束方法.
        if (root == null){
            root = new BiNode(value);
            return;
        }

        // 2.从跟开始寻当前值可在位置.
        BiNode current = root;
        boolean find = false;

        while (!find){
            if (value <= current.getValue()){
                if (current.getLeftNode() != null){
                    current = current.getLeftNode();
                }else {
                    current.setLeftNode(new BiNode(value));
                    find = true;
                }
            }else if(current.getRightNode() != null) {
                current = current.getRightNode();
            }else {
                current.setRightNode(new BiNode(value));
                find = true;
            }
        }

    }

    //添加节点
    private void add(BiNode node){

        // 1.根不存在时,新建节点,并结束方法.
        if (root == null){
            root = node;
            return;
        }

        // 2.从跟开始寻当前值可在位置.
        BiNode current = root;
        int value = node.getValue();
        boolean find = false;

        while (!find){
            if (value <= current.getValue()){
                if (current.getLeftNode() != null){
                    current = current.getLeftNode();
                }else {
                    current.setLeftNode(node);
                    find = true;
                }
            }else if(current.getRightNode() != null) {
                current = current.getRightNode();
            }else {
                current.setRightNode(node);
                find = true;
            }
        }

    }

    //删除节点
    public boolean delete(int value){
        //删除策略, 优先提升左节点.
        //如果右节点存在, 将右点为根的整树, 新加到树中.

        if (root == null){
            return false;
        }

        // 1.查找结点
        BiNode current = root;
        BiNode preNode = null;
        BiNode delNode = null;
        while (delNode == null){
            if (value == current.getValue()){
                delNode = current;
            }else if (value < current.getValue()){
                if (current.getLeftNode() != null){
                    preNode = current;
                    current = current.getLeftNode();
                }else {
                    return false;
                }
            }else if(current.getRightNode() != null) {
                preNode = current;
                current = current.getRightNode();
            }else {
                return false;
            }
        }

        // 2.删除节点
        BiNode upNode = null;   //获取提升节点
        BiNode addNode = null;  //再处理节点
        if (delNode.getLeftNode() != null){
            upNode = delNode.getLeftNode();
            if (delNode.getRightNode() != null){
                addNode = delNode.getRightNode();
            }
        }else if (delNode.getRightNode() != null){
            upNode = delNode.getRightNode();
        }

        //执行删除
        if (preNode != null){
            if (preNode.getLeftNode()==delNode){
                preNode.setLeftNode(upNode == null ? null : upNode);
            }else {
                preNode.setRightNode(upNode == null ? null : upNode);
            }
        }else {
            root = upNode == null ? null : upNode;
        }

        //再处理节点
        if (addNode != null){
            add(addNode);
        }

        return true;
    }

    //查询节点
    public boolean existValue(int value){
        // 1.根不存在时.
        if (root == null){
            return false;
        }

        // 2.从跟开始寻当前值可在位置.
        BiNode current = root;

        while (true){
            if (value == current.getValue()){
                return true;
            }else if (value < current.getValue()){
                if (current.getLeftNode() != null){
                    current = current.getLeftNode();
                }else {
                    return false;
                }
            }else if(current.getRightNode() != null) {
                current = current.getRightNode();
            }else {
                return false;
            }
        }

    }

    public void print(){
        root.print();
    }

    //调试
    public static void main(String[] args) {
        BiTree t = new BiTree();
        Random r = new Random();
        for (int i=0; i<20; i++){
            t.add(r.nextInt(100));
        }

        t.print();

        for (int i=0; i<5; i++){
            int v = r.nextInt(100);
            System.out.println(v + " if exist : " + t.existValue(v)  );
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                String cmd = bufferedReader.readLine();
                if (cmd == null || cmd.equals("")){
                    continue;
                }
                if (cmd.equals("exit")) {
                    System.exit(0);
                }else {
                    t.delete(Integer.valueOf(cmd));
                    t.print();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BiNode {

    private int value;
    private BiNode leftNode;
    private BiNode rightNode;

    public BiNode(int value){
        this.value = value;
    }

    //打印树
    public void print(){
        //获取数高度
        int offset = this.getHigth(1);
        System.out.println("high : " + offset);

        //获取每个节点的打印对象
        List<PrintUnit> list = new ArrayList<>();
        getPrintUnit(1, 0, offset, list, "", "");

        //排序
        Collections.sort(list, new Comparator<PrintUnit>() {
            @Override
            public int compare(PrintUnit o1, PrintUnit o2) {
                if (o1.getRow()==o2.getRow()){
                    return o1.getPoint()-o2.getPoint();
                }
                return o1.getRow()-o2.getRow();
            }
        });

        //计算打印点整体向右偏移数量
        int minPoint = list.get(0).getPoint();
        for (PrintUnit pu : list){
            minPoint = Math.min(minPoint, pu.getPoint());
        }
        int rightOffset = -minPoint;

        //开始打印
        int currentRow = 1;
        int currentPoint = 1;

        for (PrintUnit pu : list){
            if (currentRow != pu.getRow()){
                System.out.println();
                currentRow = pu.getRow();
                currentPoint = 1;
            }
            int ofs = pu.getPoint()+rightOffset-currentPoint;
            if (ofs > 0 ) {
                currentPoint = currentPoint + ofs;
                while (ofs > 0) {
                    System.out.print(" ");
                    ofs--;
                }
            }else {
                System.out.print("|");
                currentPoint++;
            }
            System.out.print(pu.getContent());
            currentPoint = currentPoint + pu.getContent().length();
        }
        System.out.println();
    }

    //获取每个节点的打印参数, 行数,打印位置,内容.
    private void getPrintUnit(int row, int point, int offset, List<PrintUnit> collect, String left, String right){
        collect.add(new PrintUnit(row, point,left +  String.valueOf(this.value)  + right));
        if (this.leftNode != null){
            this.leftNode.getPrintUnit( row+1, point-(offset-1), offset-1,collect ,"","/");
        }
        if (this.rightNode != null){
            this.rightNode.getPrintUnit( row+1,point+(offset-1), offset-1, collect ,"\\","");
        }
    }

    //获取树高度
    private int getHigth(int parentLevel){
        int l = parentLevel + 1;
        int r = l;

        if (this.leftNode != null){
            l = this.leftNode.getHigth(parentLevel + 1);
        }
        if (this.rightNode != null){
            r = this.rightNode.getHigth(parentLevel + 1);
        }

        return Math.max(l,r);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BiNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(BiNode leftNode) {
        this.leftNode = leftNode;
    }

    public BiNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(BiNode rightNode) {
        this.rightNode = rightNode;
    }
}


package tree;

public class PrintUnit {

    private int row;    //行
    private int point;  //起点位置
    private String content; //打印字符

    public PrintUnit(int row, int point, String content){
        this.row = row;
        this.point = point;
        this.content = content;
    }

    @Override
    public String toString() {
        return "PrintUnit{" +
                "row=" + row +
                ", point=" + point +
                ", content='" + content + '\'' +
                '}';
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
