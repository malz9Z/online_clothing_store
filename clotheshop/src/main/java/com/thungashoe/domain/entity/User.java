package com.thungashoe.domain.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@SuppressWarnings("serial")
public class User implements UserDetails{

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private String id;

    @Column(unique = true, nullable = false)
	private String username; 

	@Column(name = "avatar")
	private String urlAvatar;

    @Column(unique = true, nullable = true)
	private String email;

    @Column(unique = true, nullable = true)
	private String phone;

    @Column(nullable = false)
	private String password;
    
    @JoinColumn(name = "id_address")
	private String idAddress;
	
	@JoinColumn(name = "address")
	private String addressDetail;

	@Column(name = "deleted")
	private Boolean isDeleted;

    @ToString.Exclude
	@ManyToMany
	@JoinTable(name = "customer_vouchers", 
			joinColumns = @JoinColumn(name = "customer_id"), 
			inverseJoinColumns = @JoinColumn(name = "voucher_id"))
	private Set<Voucher> vouchers;
	
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "favorite_products", 
			joinColumns = @JoinColumn(name = "customer_id"), 
			inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> favoriteProducts;
	
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

	@Override 
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !this.isDeleted;
	}
}
