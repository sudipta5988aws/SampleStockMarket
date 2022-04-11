package com.jpmc.app.dataobjects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;


@MappedSuperclass
@Getter
@Setter
@ToString
public class PersistenceEntity implements Serializable {
	
	private static final long serialVersionUID = 7795098002767234902L;
	
	@Column(columnDefinition = "CHAR(32)")
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy = "uuid")
	private String id;
	
	
	@Column(name="creation_date")
	@CreationTimestamp
	private LocalDateTime creationDate;
	
	@Column(name="modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	
	
	@Override
	public boolean equals(Object o) {
		boolean equals=true;
		if (o instanceof PersistenceEntity) {
			if(( (PersistenceEntity) o).getId() ==null && this.getId() == null) {
				equals=super.equals(o);
			}
			else if(Objects.nonNull(((PersistenceEntity) o).getId()) & Objects.nonNull(this.getId())
					&& ((PersistenceEntity) o).getId().equals(this.getId())) {
				equals=true;
			}
		}
		return equals;
	}
	
	
	@Override
	public int hashCode() {
		if(Objects.nonNull(id)) {
			return this.id.hashCode();
		}
		else {
			return super.hashCode();
		}
	}

}
