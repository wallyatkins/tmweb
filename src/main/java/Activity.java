import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Activity {

	private String level;
	private String type;
	private String title;
	private LocalDate start;
	private LocalDate end;
	private String location;
	private String id;
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public String getEnd() {
		return end.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
