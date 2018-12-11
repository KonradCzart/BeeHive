package com.beehive.infrastructure.payload;

public class UserDTO {
	
    private Long id;
    private String username;
    private String name;
    private String email;
    

    private UserDTO(UserDTOBuilder builder) {
        id = builder.id;
        username = builder.username;
        name = builder.name;
        email = builder.email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static UserDTOBuilder builder() {
    	return new UserDTOBuilder();
    }
    
    public static class UserDTOBuilder {
    	
    	private Long id;
        private String username;
        private String name;
        private String email;
        
        public UserDTOBuilder withId(Long id) {
        	this.id = id;
        	return this;
        }
        
        public UserDTOBuilder withUsername(String username) {
			this.username = username;
			return this;
		}
        
        public UserDTOBuilder withName(String name) {
			this.name = name;
			return this;
		}
        
        public UserDTOBuilder withEmail(String email) {
			this.email = email;
			return this;
		}
        
        public UserDTO build() {
			return new UserDTO(this);
		}
        
    }
    
}
