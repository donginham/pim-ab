package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Main;
import controller.MemberService;
import controller.MemberServiceImpl;
import examples.TableViewTest.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Member;

public class MemberViewController implements Initializable {
	@FXML	private Button btnCreate;
	@FXML	private Button btnUpdate;
	@FXML	private Button btnDelete;
	@FXML	private Button btnSearchID;
	
	@FXML	private Button btnReol;
	@FXML	private TextArea taExecute;
	@FXML	private TextField tfExecute;
	
	@FXML	private TextField tfID;
	@FXML	private PasswordField tfPW;
	@FXML	private TextField tfName;
	@FXML	private TextField tfContact;
	
	@FXML 	private TableView<Member> tableViewMember;
	@FXML	private TableColumn<Member, String> columnName;
	@FXML	private TableColumn<Member, String> columnID;
	@FXML	private TableColumn<Member, String> columnPW;
	@FXML	private TableColumn<Member, String> columnContact;
	// Member : model 이라고도 하고, DTO, Vo 라고도 함
	// 시스템 밖에 저장된 객체들간에 사용하는 정보를 변환한 자료구조 또는 객체
	private final ObservableList<Member> data = FXCollections.observableArrayList();
	// 목록 : 이중 연결 리스트는 아니지만, 리스트의 특징과 배열의 특징을 잘 혼용해 놓은 클래스 ArrayList
	ArrayList<Member> memberList;
	MemberService memberService;
	
	
	public MemberViewController() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		memberService = new MemberServiceImpl();
		// 람다식 : java 8 함수형 언어 지원
		
		
		columnID.setCellValueFactory(cvf -> cvf.getValue().uidProperty());
		columnPW.setCellValueFactory(cvf -> cvf.getValue().upwProperty());
		columnName.setCellValueFactory(cvf -> cvf.getValue().unameProperty());
		columnContact.setCellValueFactory(cvf -> cvf.getValue().ContactProperty());
		
		tableViewMember.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showMemberInfo(newValue));

		btnCreate.setOnMouseClicked(event -> handleCreate());		
		btnDelete.setOnMouseClicked(e -> handleDelete());		
		btnReol.setOnMouseClicked(event -> handleExecute());	
		btnSearchID.setOnMouseClicked(event -> handleCreate());
		
		loadMemberTableView();
	}
	@FXML
	private void handleReol() {
		taExecute.setText("Bless REOL");
		System.out.println("Bless REOL");
	}
	
	String str = ""; // 인스턴스 변수 - 객체 변수, 객체가 존재하는 동안 메모리에 존재
	@FXML 
	private void handleExecute() { // event source, listener, handler
		str = str + tfExecute.getText() + "\n";
		taExecute.setText(str);
	}
	
	private void showMemberInfo(Member member) {
		if (member != null) {
			tfID.setText(member.getUid());
			tfPW.setText(member.getUpw());
			tfName.setText(member.getUname());
			tfContact.setText(member.getContact());
		}
		 else {
			 tfID.setText("");
			 tfPW.setText("");
		     tfName.setText("");
		     tfContact.setText("");
		 }
	}
	
	private void loadMemberTableView() {
		memberList = memberService.readList();
		for(Member m : memberList) {
			data.add(m);
		}
		tableViewMember.setItems(data);
	}
	
	/*@FXML
	private void handleSearchID() {
		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			memberService.delete(tableViewMember.getItems().remove(selectedIndex));			
		} else {
			showAlert("삭제를 실패하였습니다.");
        }
	}*/

	@FXML 
	private void handleCreate() { // event source, listener, handler
		if(tfID.getText().length() > 0) {
			Member newMember = new Member(tfID.getText(), tfPW.getText(), tfName.getText(), tfContact.getText());
			data.add(newMember);			
			tableViewMember.setItems(data);
			memberService.create(newMember);
		} else
			showAlert("ID 입력오류");
	}
	@FXML 
	private void handleUpdate() {
		Member newMember = new Member(tfID.getText(), tfPW.getText(), tfName.getText(), tfContact.getText());

		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			tableViewMember.getItems().set(selectedIndex, newMember);
			memberService.update(newMember);			
		} else {
			showAlert("업데이트를 실패하였습니다.");          
        }
	}
	
	@FXML 
	private void handleDelete() {
		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			memberService.delete(tableViewMember.getItems().remove(selectedIndex));			
		} else {
			showAlert("삭제를 실패하였습니다.");
        }
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(mainApp.getRootStage());
        alert.setTitle("Ȯ��");
        alert.setContentText("Ȯ�� : " + message);            
        alert.showAndWait();
	}

	private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
