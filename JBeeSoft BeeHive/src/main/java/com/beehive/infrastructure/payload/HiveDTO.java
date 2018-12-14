package com.beehive.infrastructure.payload;

public class HiveDTO {
	
	private Long id;
	private String name;
	private String typeName;
	private int boxNumber;
	private BeeQueenDTO queenDTO;
	
	public HiveDTO() {
		
	}
	
	public HiveDTO(HiveDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.typeName = builder.typeName;
		this.boxNumber = builder.boxNumber;
		this.queenDTO = builder.queenDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String tapeName) {
		this.typeName = tapeName;
	}

	public int getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}

	public BeeQueenDTO getQueenDTO() {
		return queenDTO;
	}

	public void setQueenDTO(BeeQueenDTO queenDTO) {
		this.queenDTO = queenDTO;
	}
	
	public static HiveDTOBuilder builder() {
		return new HiveDTOBuilder();
	}
	
	public static class HiveDTOBuilder {
		
		private Long id;
		private String name;
		private String typeName;
		private int boxNumber;
		private BeeQueenDTO queenDTO;
		
		public HiveDTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public HiveDTOBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public HiveDTOBuilder withTypeName(String typeName) {
			this.typeName = typeName;
			return this;
		}
		
		public HiveDTOBuilder withBoxNumber(int boxNumber) {
			this.boxNumber = boxNumber;
			return this;
		}
		
		public HiveDTOBuilder withQueenDTO(BeeQueenDTO queenDTO) {
			this.queenDTO = queenDTO;
			return this;
		}
		
		public HiveDTO build() {
			return new HiveDTO(this);
		}
	}
	
}
